%{
#include<stdlib.h>
#include<stdio.h>
#define YYDEBUG 1
//#include"syntabs.h" // pour syntaxe abstraite
//extern n_prog *n;   // pour syntaxe abstraite
extern FILE *yyin;    // declare dans compilo
extern int yylineno;  // declare dans analyseur lexical
int yylex();          // declare dans analyseur lexical
int yyerror(char *s); // declare ci-dessous
%}

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
%token IDENTIF

//Type
%token ENTIER

//Type de valeur
%token NOMBRE

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

%start programme
%%

//Grammaire des expressions arithmétiques

programme : declaration programme
|           expression programme
|           instruction programme
|
;

//declaration & affectation

declaration : ENTIER var POINT_VIRGULE
|             ENTIER var VIRGULE declaration
|             ENTIER var CROCHET_OUVRANT NOMBRE CROCHET_FERMANT POINT_VIRGULE
;

var : IDENTIF
|     IDENTIF CROCHET_OUVRANT expression CROCHET_FERMANT //(2 shift/reduce conflicts)
;

//expressions

expression : expression OU e1
|            e1
;

e1 : e1 ET e2
|    e2
;

e2 : e2 EGAL e3 //(2 shift/reduce conflicts)
|    e2 INFERIEUR e3
|    e3
;

e3 : e3 PLUS e4
|    e3 MOINS e4
|    e4
;

e4 : e4 FOIS e5
|    e4 DIVISE e5
|    e5
;

e5 : NON e5
|    e6
;

e6 : PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE
|    NOMBRE
|    var
|    lecture
|    declarationfct
;

listexpression : expression
|                expression VIRGULE listexpression
|                ENTIER var VIRGULE listexpression
|                ENTIER var
|
;


//fonctions

declarationfct : IDENTIF PARENTHESE_OUVRANTE listexpression PARENTHESE_FERMANTE; /*(useless car var = identif)*/

appelfct : IDENTIF PARENTHESE_OUVRANTE listexpression PARENTHESE_FERMANTE POINT_VIRGULE;



//instructions

instruction : instru_affect
|             instru_retour
|             instru_si
|             instru_tantque
|             instru_bloc
|             appelfct
//|             declarationfct
|             ecriture
;

instru_affect : var EGAL expression POINT_VIRGULE //(1 shift/reduce conflict)
|               var CROCHET_OUVRANT expression CROCHET_FERMANT EGAL expression POINT_VIRGULE
;

instru_retour : RETOUR expression POINT_VIRGULE;

instru_si : SI expression ALORS instru_bloc
|           SI expression ALORS instru_bloc SINON instru_bloc
//|           SI PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE ALORS instru_bloc
//|           SI PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE ALORS instru_bloc SINON instru_bloc
;

instru_tantque : TANTQUE expression FAIRE instru_bloc;

instru_bloc : ACCOLADE_OUVRANTE contenu_bloc ACCOLADE_FERMANTE;

contenu_bloc : instruction contenu_bloc
//|              expression contenu_bloc
|
;

lecture : LIRE PARENTHESE_OUVRANTE PARENTHESE_FERMANTE;

ecriture : ECRIRE PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE POINT_VIRGULE;
/*
*/

%%

int yyerror(char *s) {
  fprintf(stderr, "erreur de syntaxe ligne %d\n", yylineno);
  fprintf(stderr, "%s\n", s);
  fclose(yyin);
  exit(1);
}
