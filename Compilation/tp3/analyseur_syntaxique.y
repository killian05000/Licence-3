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

%type <prog> programme

%start programme
%%

// Axiome de la grammaire

programme : ligneDecVar ensDefFct;


// Grammaire des declarations de variables

ligneDecVar : suiteDecVar POINT_VIRGULE
|
;

suiteDecVar: decVar VIRGULE suiteDecVar
		|	decVar
		;

decVar : ENTIER IDENTIF
		|	ENTIER IDENTIF CROCHET_OUVRANT NOMBRE CROCHET_FERMANT				//Pas de var/expArith pour la taille?
		;


// Grammaire des definitions de fonctions

ensDefFct : defFct ensDefFct
		|	defFct
		;


defFct : IDENTIF PARENTHESE_OUVRANTE decArgs PARENTHESE_FERMANTE ligneDecVar intru_bloc

decArgs : suiteDecVar
		|
		;




// grammaire des expressions arithmetiques
e1 : e1 OU e2
		|	e2
		;

e2 : e2 ET e3
		|	e3
		;

e3	: e3 EGAL e4
		|	e3 INFERIEUR e4
		| e4
		;

e4 : e4 PLUS e5
		|	e4 MOINS e5
		|	e5
		;

e5 : e5 FOIS e6
		|	e5 DIVISE e6
		|	e6
		;

e6 : NON e6
		| e7
		;

e7 : PARENTHESE_OUVRANTE e1 PARENTHESE_FERMANTE
		|	varAcces
		|	NOMBRE																					//Attention Valeur (pas pointeur)
		| fonction
		| LIRE PARENTHESE_OUVRANTE PARENTHESE_FERMANTE
		;

varAcces : IDENTIF
		|	IDENTIF CROCHET_OUVRANT e1 CROCHET_FERMANT
		;

fonction : IDENTIF PARENTHESE_OUVRANTE argument PARENTHESE_FERMANTE;

argument : e1 listArg
		|
		;

listArg : VIRGULE e1	listArg
		|
		;


// Grammaire des instructions

instruction : intru_affect
		|	condition
		|	intru_boucle
		|	intru_retour
		|	appelFonction
		|	intru_bloc
		|	POINT_VIRGULE
		;

intru_affect : varAcces EGAL e1 POINT_VIRGULE

condition : SI e1 ALORS intru_bloc
		|	SI e1 ALORS intru_bloc SINON intru_bloc
		;

intru_boucle : TANTQUE e1 FAIRE intru_bloc

intru_retour : RETOUR e1 POINT_VIRGULE

appelFonction : fonction POINT_VIRGULE			// Manque Lire...
		|	ECRIRE PARENTHESE_OUVRANTE e1 PARENTHESE_FERMANTE POINT_VIRGULE
		;

intru_bloc : ACCOLADE_OUVRANTE listeInstru ACCOLADE_FERMANTE
		;

listeInstru : instruction listeInstru
		|
		;


%%

int yyerror(char *s) {
  fprintf(stderr, "erreur de syntaxe ligne %d\n", yylineno);
  fprintf(stderr, "%s\n", s);
  fclose(yyin);
  exit(1);
}
