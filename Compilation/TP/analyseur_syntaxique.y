%{
#include<stdlib.h>
#include<stdio.h>
#define YYDEBUG 1
#include "affiche_arbre_abstrait.h"
#include"syntabs.h" // pour syntaxe abstraite
extern n_prog *n;   // pour syntaxe abstraite
extern FILE *yyin;    // declare dans compilo
extern int yylineno;  // declare dans analyseur lexical
int yylex();          // declare dans analyseur lexical
int yyerror(char *s); // declare ci-dessous
%}

%code requires
{
  #include "syntabs.h"
}

%union{
  char* cvalue;
  int ivalue;
  n_prog* prog;
  n_l_dec* l_dec;
	n_dec* dec;
	n_l_instr* l_instr;
	n_instr* instr;
	n_l_exp* l_exp;
	n_exp* exp;
	n_var* var;
  n_appel* appel;
}

//symboles
%token VIRGULE
%token POINT_VIRGULE
%token PARENTHESE_OUVRANTE
%token PARENTHESE_FERMANTE
%token CROCHET_OUVRANT
%token CROCHET_FERMANT
%token ACCOLADE_OUVRANTE
%token ACCOLADE_FERMANTE

//operators
%token PLUS
%token MOINS
%token FOIS
%token DIVISE
%token EGAL
%token INFERIEUR
%token ET
%token OU
%token NON

//variable
%token <cvalue> IDENTIF

//Type
%token ENTIER

//Type de valeur
%token <ivalue> NOMBRE

//Condition
%token SI
%token ALORS
%token SINON

//loop
%token TANTQUE
%token FAIRE

//function
%token LIRE
%token ECRIRE
%token RETOUR

//arbre de dérivation
//******Types******//

%type <prog>  programme

%type <dec> defFct
%type <l_dec> ensDefFct

%type <l_dec> decArgs
%type <dec> decVar
%type <l_dec> listeDecVar
%type <l_dec> ligneDecVar

%type <instr> instru_affect condition instru_boucle instru_retour appelFonction instru_bloc

%type <appel> fonction

%type <var> varAcces

%type <l_instr> ensembleInstructions
%type <instr> instruction

%type <l_exp> argument listArg
%type <exp> expression e1 e2 e3 e4 e5 e6



%start programme

%%


programme : ligneDecVar ensDefFct	{$$ = n = cree_n_prog($1, $2);};


//******************Grammaire des declarations (dec) de variables******************//

ligneDecVar : listeDecVar POINT_VIRGULE 	{$$ = $1;}
|               													{$$=NULL;}
;

listeDecVar: decVar VIRGULE listeDecVar 	{$$ = cree_n_l_dec($1, $3);}
|	           decVar											  {$$ = cree_n_l_dec($1, NULL);}
;

decVar : ENTIER IDENTIF									              	          {$$ = cree_n_dec_var($2);}
|	       ENTIER IDENTIF CROCHET_OUVRANT NOMBRE CROCHET_FERMANT  	{$$ = cree_n_dec_tab($2, $4);}
;


//******************Grammaire des definitions (def) de fonctions******************//

ensDefFct : defFct ensDefFct 	  {$$ = cree_n_l_dec($1, $2);}
|         	defFct						  {$$ = cree_n_l_dec($1, NULL);}
;


defFct : IDENTIF PARENTHESE_OUVRANTE decArgs PARENTHESE_FERMANTE ligneDecVar instru_bloc 	{$$ = cree_n_dec_fonc($1, $3, $5, $6);};

decArgs : listeDecVar			{$$ = $1;}
|										      {$$ = NULL;}
;


//******************Grammaire des expressions******************//


expression : expression OU e1 	{$$ = cree_n_exp_op(ou, $1, $3);}
|	           e1									{$$ = $1;}
;

e1 : e1 ET e2	  {$$ = cree_n_exp_op(et, $1, $3);}
|    e2					{$$ = $1;}
;

e2	: e2 EGAL e3	    	{$$ = cree_n_exp_op(egal, $1, $3);}
|	    e2 INFERIEUR e3		{$$ = cree_n_exp_op(inferieur, $1, $3);}
|     e3								{$$ = $1;}
;

e3 : e3 PLUS e4		  {$$ = cree_n_exp_op(plus, $1, $3);}
|  	 e3 MOINS e4	  {$$ = cree_n_exp_op(moins, $1, $3);}
|	   e4				      {$$ = $1;}
;

e4 : e4 FOIS e5		  {$$ = cree_n_exp_op(fois, $1, $3);}
|    e4 DIVISE e5		{$$ = cree_n_exp_op(divise, $1, $3);}
|	   e5	            {$$ = $1;}
;

e5 : NON e5   {$$ = cree_n_exp_op(non, $2, NULL);}
|    e6		    {$$ = $1;}
;

e6 : PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE		{$$ = $2;}
|    varAcces                                             {$$ = cree_n_exp_var($1);}
|    NOMBRE		                                            {$$ = cree_n_exp_entier($1);}
|    fonction                                         	  {$$ = cree_n_exp_appel($1);}
|    LIRE PARENTHESE_OUVRANTE PARENTHESE_FERMANTE	      	{$$ = cree_n_exp_lire();}
;

varAcces : IDENTIF                                              {$$ = cree_n_var_simple($1);}
|          IDENTIF CROCHET_OUVRANT expression CROCHET_FERMANT	  {$$ = cree_n_var_indicee($1, $3);}
;

fonction : IDENTIF PARENTHESE_OUVRANTE argument PARENTHESE_FERMANTE		{$$ = cree_n_appel($1, $3);};

argument : expression listArg	{$$=cree_n_l_exp($1,$2);}
|           									{$$ = NULL;}
;

listArg : VIRGULE expression	listArg		{$$ = cree_n_l_exp($2, $3);}
|												                {$$=NULL;}
;


//******************Grammaire des instructions******************//


instruction : instru_affect		{$$ = $1;}
|	            condition		    {$$ = $1;}
|             instru_boucle		{$$ = $1;}
|	            instru_retour		{$$ = $1;}
|	            appelFonction		{$$ = $1;}
|	            instru_bloc 	  {$$ = $1;}
|	            POINT_VIRGULE		{$$ = cree_n_instr_vide();}
;

instru_affect : varAcces EGAL expression POINT_VIRGULE    {$$ = cree_n_instr_affect($1, $3);};

condition : SI expression ALORS instru_bloc	                    {$$ = cree_n_instr_si($2, $4, NULL);}
|         	SI expression ALORS instru_bloc SINON instru_bloc		{$$ = cree_n_instr_si($2, $4, $6);}
;

instru_boucle : TANTQUE expression FAIRE instru_bloc 	  {$$ = cree_n_instr_tantque($2, $4);};

instru_retour : RETOUR expression POINT_VIRGULE   {$$ = cree_n_instr_retour($2);};

appelFonction : fonction POINT_VIRGULE		                                                {$$ = cree_n_instr_appel($1);}
|             	ECRIRE PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE POINT_VIRGULE		{$$ = cree_n_instr_ecrire($3);}
;

instru_bloc : ACCOLADE_OUVRANTE ensembleInstructions ACCOLADE_FERMANTE 	{$$ = cree_n_instr_bloc($2);};

ensembleInstructions : instruction ensembleInstructions		{$$ = cree_n_l_instr($1, $2);}
|													                                {$$=NULL;}
;


%%

int yyerror(char *s) {
  fprintf(stderr, "erreur de syntaxe ligne %d\n", yylineno);
  fprintf(stderr, "%s\n", s);
  fclose(yyin);
  exit(1);
}
