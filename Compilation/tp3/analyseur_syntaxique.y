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

programme : ligneDeclarationsVar ensembleDefinitionFct;


// Grammaire des declarations de variables

ligneDeclarationsVar : suiteDeclarationsVar POINT_VIRGULE
		|
		;

suiteDeclarationsVar: declarationVar VIRGULE suiteDeclarationsVar
		|	declarationVar
		;

declarationVar : ENTIER IDENTIF
		|	ENTIER IDENTIF CROCHET_OUVRANT NOMBRE CROCHET_FERMANT				//Pas de var/expArith pour la taille?
		;


// Grammaire des definitions de fonctions

ensembleDefinitionFct : definitionFct ensembleDefinitionFct
		|	definitionFct
		;


definitionFct : IDENTIF PARENTHESE_OUVRANTE declarationsArgs PARENTHESE_FERMANTE ligneDeclarationsVar blocInstructions

declarationsArgs : suiteDeclarationsVar
		|
		;




// grammaire des expressions arithmetiques
expressionArithmetique : expressionArithmetique OU conjonction
		|	conjonction
		;

conjonction : conjonction ET comparaison
		|	comparaison
		;

comparaison	: comparaison EGAL somme
		|	comparaison INFERIEUR somme
		| somme
		;

somme : somme PLUS produit
		|	somme MOINS produit
		|	produit
		;

produit : produit FOIS negation
		|	produit DIVISE negation
		|	negation
		;

negation : NON negation
		| expressionPrioritaire
		;

expressionPrioritaire : PARENTHESE_OUVRANTE expressionArithmetique PARENTHESE_FERMANTE
		|	varAcces
		|	NOMBRE																					//Attention Valeur (pas pointeur)
		| 	fonction
		|  	LIRE PARENTHESE_OUVRANTE PARENTHESE_FERMANTE
		;

varAcces : IDENTIF
		|	IDENTIF CROCHET_OUVRANT expressionArithmetique CROCHET_FERMANT
		;

fonction : IDENTIF PARENTHESE_OUVRANTE argument PARENTHESE_FERMANTE;

argument : expressionArithmetique listArg
		|
		;

listArg : VIRGULE expressionArithmetique	listArg
		|
		;


// Grammaire des instructions

instruction : affectation
		|	condition
		|	boucle
		|	retour
		|	appelFonction
		|	blocInstructions
		|	POINT_VIRGULE
		;

affectation : varAcces EGAL expressionArithmetique POINT_VIRGULE

condition : SI expressionArithmetique ALORS blocInstructions
		|	SI expressionArithmetique ALORS blocInstructions SINON blocInstructions
		;

boucle : TANTQUE expressionArithmetique FAIRE blocInstructions

retour : RETOUR expressionArithmetique POINT_VIRGULE

appelFonction : fonction POINT_VIRGULE			// Manque Lire...
		|	ECRIRE PARENTHESE_OUVRANTE expressionArithmetique PARENTHESE_FERMANTE POINT_VIRGULE
		;

blocInstructions : ACCOLADE_OUVRANTE ensembleInstructions ACCOLADE_FERMANTE
		;

ensembleInstructions : instruction ensembleInstructions
		|
		;


%%

int yyerror(char *s) {
  fprintf(stderr, "erreur de syntaxe ligne %d\n", yylineno);
  fprintf(stderr, "%s\n", s);
  fclose(yyin);
  exit(1);
}
