CREATE OR REPLACE FUNCTION CreerEnsAttVide RETURN NUMBER 
IS 
    var NUMBER;
BEGIN
    var := NumEnsAtt.NextVal;
    INSERT INTO EnsembleAttributs VALUES(var);
    RETURN var;
END CreerEnsAttVide;

CREATE OR REPLACE PROCEDURE AjouterAtt(p_nomAtt varchar, p_NumEnsAtt number) IS
BEGIN
    INSERT INTO ENSEMBLECONTIENTATTRIBUT(nomatt,numensatt) VALUES(p_nomAtt, p_NumEnsAtt);
END;

CREATE OR REPLACE FUNCTION CreerEnsAtt(p_ChaineAtt varchar) return NUMBER
BEGIN
    


DECLARE V NUMBER;
BEGIN
V := CreerEnsAttVide;
END;

Execute AjouterAtt('TRUC',42);