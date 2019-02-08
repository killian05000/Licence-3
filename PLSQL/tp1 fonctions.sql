CREATE OR REPLACE FUNCTION CreerEnsAttVide RETURN NUMBER 
IS 
    var NUMBER;
BEGIN
    var := NumEnsAtt.NextVal;
    INSERT INTO EnsembleAttributs VALUES(var);
    RETURN var;
END CreerEnsAttVide;
/

CREATE OR REPLACE PROCEDURE AjouterAtt(p_nomAtt varchar, p_NumEnsAtt number) IS
BEGIN
    INSERT INTO ENSEMBLECONTIENTATTRIBUT(nomatt,numensatt) VALUES(p_nomAtt, p_NumEnsAtt);
END AjouterAtt;
/

CREATE OR REPLACE FUNCTION CreerEnsAtt(p_ChaineAtt varchar) return NUMBER
IS
    debut NUMBER;
    nom VARCHAR(50);
    pos NUMBER;
    numEns NUMBER;
BEGIN
    numEns := CreerEnsAttVide;
    debut := 1;
    pos := INSTR(p_ChaineAtt, ',', debut);
    
    WHILE pos != 0 LOOP
        nom := SUBSTR(p_ChaineAtt, debut, pos-debut);
        AjouterAtt(trim(nom), numEns); 
        debut := pos+1;
        pos := INSTR(p_ChaineAtt, ',', debut);
    END LOOP; 
    
    pos := length(p_ChaineAtt)+1;
    IF debut<pos THEN
        nom := SUBSTR(p_ChaineAtt, debut, pos-debut);
        AjouterAtt(trim(nom), numEns);
    END IF;        
    
    RETURN numEns;
END CreerEnsAtt;
/

CREATE OR REPLACE FUNCTION EnsAtt2Chaine(p_NumEnsAtt number) return VARCHAR
IS
    chaine VARCHAR(2000);
    CURSOR curseur IS (SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt);
BEGIN

    FOR ligne IN curseur LOOP
        chaine := chaine ||ligne.NOMATT||',';
    END LOOP;
    chaine := SUBSTR(chaine, 1, length(chaine)-1);
    
    RETURN chaine;
END EnsAtt2Chaine;
/

CREATE OR REPLACE FUNCTION EstElement(p_NomAtt varchar, p_NumEnsAtt number) return NUMBER
IS
    bool NUMBER;
    chaine VARCHAR(2000);
    CURSOR curseur IS SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt AND NOMATT=p_NomAtt;
BEGIN

    bool := 0;
    --SELECT NOMATT INTO chaine FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt AND NOMATT=p_NomAtt;
    FOR ligne IN curseur LOOP
        bool := 1;
    END LOOP;         
    
    RETURN bool;    
END EstElement;
/

    
Execute AjouterAtt('PARIS',5);

DECLARE V NUMBER;
BEGIN
V := CreerEnsAtt('COUCOU,SALUT,ABCDEFG,X');
END;

SELECT EnsAtt2Chaine(6) from dual;
SELECT EstElement('AZERTY',4) from dual;