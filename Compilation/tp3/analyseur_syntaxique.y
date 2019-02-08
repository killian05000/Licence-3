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

%%

//Grammaire des expressions arithmétiques

expression : expression OU et
|            et
;

et : et ET egalite
|   egalite
;

egalite : egalite EGAL plusmoins
|         egalite INFERIEUR plusmoins
|         plusmoins
;

plusmoins : plusmoins PLUS multidiv
|           plusmoins MOINS multidiv
|           multidiv
;

multidiv : multidiv FOIS negation
|          multidiv DIVISE negation
|          negation
;

negation : NON negation
|          parenthese
;

parenthese : PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE
|            NOMBRE
|            appelfct
|            LIRE
|            var
;

var : IDENTIF
|     IDENTIF CROCHET_OUVRANT expression CROCHET_FERMANT
;

appelfct : IDENTIF PARENTHESE_OUVRANTE listexpression PARENTHESE_FERMANTE
;

listexpression : expression
|                expression VIRGULE listexpression
|
;

//Grammaire des instructions

instruction : affectation
|             retour
|             si
|             tantque
|             bloc
|             appelfct
|             vide
;

affectation : var EGAL expression
;

retour : RETOUR expression
;

si : SI PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE ALORS bloc
|    SI PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE ALORS bloc SINON bloc
;

tantque : TANTQUE expression FAIRE bloc
;

bloc : CROCHET_OUVRANT b CROCHET_FERMANT
;

b : instruction b
|
;

vide :
;

lecture : var EGAL LIRE
;

ecriture : ECRIRE PARENTHESE_OUVRANTE expression PARENTHESE_FERMANTE
;


%%

int yyerror(char *s) {
  fprintf(stderr, "erreur de syntaxe ligne %d\n", yylineno);
  fprintf(stderr, "%s\n", s);
  fclose(yyin);
  exit(1);
}
