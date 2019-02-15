SET SERVEROUTPUT ON;

CREATE OR REPLACE FUNCTION CreerEnsAttVide RETURN NUMBER 
IS 
    var NUMBER;
BEGIN
    var := NumEnsAtt.NextVal;
    INSERT INTO EnsembleAttributs VALUES(var);
    RETURN var;
END CreerEnsAttVide;
/

/******************************************************************/

CREATE OR REPLACE PROCEDURE AjouterAtt(p_nomAtt varchar, p_NumEnsAtt number) IS
BEGIN
    INSERT INTO ENSEMBLECONTIENTATTRIBUT(nomatt,numensatt) VALUES(p_nomAtt, p_NumEnsAtt);
END AjouterAtt;
/

/******************************************************************/

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

/******************************************************************/

CREATE OR REPLACE FUNCTION EnsAtt2Chaine(p_NumEnsAtt number) return VARCHAR
IS
    chaine VARCHAR(2000);
    CURSOR curseur IS SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt ORDER BY NOMATT;
BEGIN

    FOR ligne IN curseur LOOP
        chaine := chaine ||ligne.NOMATT||',';
    END LOOP;
    chaine := SUBSTR(chaine, 1, length(chaine)-1);
    
    RETURN chaine;
END EnsAtt2Chaine;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EstElement(p_NomAtt varchar, p_NumEnsAtt number) return NUMBER
IS
    bool NUMBER;
    CURSOR curseur IS SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt AND NOMATT=p_NomAtt;
BEGIN

    bool := 0;
    FOR ligne IN curseur LOOP
        bool := 1;
    END LOOP;         
    
    RETURN bool;    
END EstElement;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EstInclus (p_NumEnsAtt_1 number, p_NumEnsAtt_2 number) return INTEGER
IS
    bool NUMBER;
    counter NUMBER;
    i NUMBER;
    chaine VARCHAR(2000);
    CURSOR curseur_1 IS (SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt_1);
BEGIN
    bool :=0;
    counter :=0;
    i :=0;
    chaine := EnsAtt2Chaine(p_NumEnsAtt_2);
    
    FOR ligne IN curseur_1 LOOP
        i := i+1;
        IF (INSTR(chaine, ligne.NOMATT) != 0) OR (INSTR(chaine, ligne.NOMATT) IS NULL) THEN
            counter := counter+1;
        END IF;  
    END LOOP;
    
    IF counter = i THEN
        bool := 1;
    END IF;
    RETURN bool;
END EstInclus;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EstEgal(p_NumEnsAtt_1 number, p_NumEnsAtt_2 number) return INTEGER
IS
    bool NUMBER;
    chaine_1 VARCHAR(2000);
    chaine_2 VARCHAR(2000);
BEGIN
    bool :=0;
    chaine_1 := EnsAtt2Chaine(p_NumEnsAtt_1);
    chaine_2 := EnsAtt2Chaine(p_NumEnsAtt_2);    

    IF chaine_1 = chaine_2 THEN
        bool :=1;
    END IF;  

    RETURN bool;
END EstEgal;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION UnionAtt(p_NumEnsAtt1 number, p_NumEnsAtt2 number) return NUMBER
IS
    num NUMBER;
BEGIN
    num := CreerEnsAttVide();
    insert into ENSEMBLECONTIENTATTRIBUT SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt1 UNION
    SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt2;
    
    RETURN num;
END UnionAtt;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION IntersectionAtt(p_NumEnsAtt1 number, p_NumEnsAtt2 number) return NUMBER
IS
    num NUMBER;
BEGIN

    num := CreerEnsAttVide();
    insert into ENSEMBLECONTIENTATTRIBUT SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt1 INTERSECT
    SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt2;
    
    RETURN num;
END IntersectionAtt;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION SoustractionAtt(p_NumEnsAtt1 number, p_NumEnsAtt2 number) return NUMBER
IS
    num NUMBER;
BEGIN

    num := CreerEnsAttVide();
    insert into ENSEMBLECONTIENTATTRIBUT SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt1 MINUS
    SELECT num,NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt2;
    
    RETURN num;
END SoustractionAtt;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION CopieAtt(p_NumEnsAtt number) return NUMBER
IS
    num NUMBER;
    CURSOR curseur IS SELECT NOMATT FROM ENSEMBLECONTIENTATTRIBUT WHERE NUMENSATT=p_NumEnsAtt;
BEGIN

    num := CreerEnsAttVide();
    FOR ligne IN curseur LOOP
        AjouterAtt(ligne.NOMATT, num);
    END LOOP;
    
    RETURN num;
END CopieAtt;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION CreerDF(p_ChaineAtt varchar) return NUMBER
IS
    chaine1 VARCHAR(2000);
    chaine2 VARCHAR(2000);
    numEnsG NUMBER;
    numENSD NUMBER;
    var NUMBER;
    position NUMBER;
BEGIN
    var := numDF.NextVal;
    dbms_output.put_line(var);
    position := INSTR(p_ChaineAtt, '->',1);
    dbms_output.put_line(position);
    chaine1 := SUBSTR(p_ChaineAtt, 1, position-1);
    dbms_output.put_line(chaine1);
    chaine2 := SUBSTR(p_ChaineAtt, (length(chaine1)+3), (length(p_ChaineAtt)-(length(chaine1))));
    dbms_output.put_line(chaine2);
    numEnsG := CreerEnsAtt(chaine1);
    numEnsD := CreerEnsAtt(chaine2);
    insert INTO DFS(NumDF, NumEnsGauche, NumEnsDroit) VALUES (var,numEnsG, numEnsD);
    return var;
END CreerDF;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION DF2Chaine(p_NumDF number) return varchar
IS
    chaine1 VARCHAR(2000);
    chaine2 VARCHAR(2000);
    chaine3 VARCHAR(4000);
    numEns1 NUMBER;
    numEns2 NUMBER;
    CURSOR curseur1 IS SELECT NumEnsGauche FROM DFS WHERE NUMDF=p_NumDF;
    CURSOR curseur2 IS SELECT NumEnsDroit FROM DFS WHERE NUMDF=p_NumDF;
BEGIN
    FOR ligne IN curseur1 LOOP
        numEns1 := ligne.NumEnsGauche;
    END LOOP;
    
    FOR ligne IN curseur2 LOOP
        numEns2 := ligne.NumEnsDroit;
    END LOOP;

    chaine1 := EnsAtt2Chaine(numEns1);
    chaine2 := EnsAtt2Chaine(numEns2);
    chaine3 := chaine1 || '->' || chaine2;
    
    RETURN chaine3;
END DF2Chaine;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION DF2ChaineVirgule(p_NumDF number) return varchar --Fonction utilisé uniquement pour EnsDF2EnsAtt
IS
    chaine1 VARCHAR(2000);
    chaine2 VARCHAR(2000);
    chaine3 VARCHAR(4000);
    numEns1 NUMBER;
    numEns2 NUMBER;
    CURSOR curseur1 IS SELECT NumEnsGauche FROM DFS WHERE NUMDF=p_NumDF;
    CURSOR curseur2 IS SELECT NumEnsDroit FROM DFS WHERE NUMDF=p_NumDF;
BEGIN
    FOR ligne IN curseur1 LOOP
        numEns1 := ligne.NumEnsGauche;
    END LOOP;
    
    FOR ligne IN curseur2 LOOP
        numEns2 := ligne.NumEnsDroit;
    END LOOP;

    chaine1 := EnsAtt2Chaine(numEns1);
    chaine2 := EnsAtt2Chaine(numEns2);
    chaine3 := chaine1 || ',' || chaine2;
    
    RETURN chaine3;
END DF2ChaineVirgule;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EstTriviale(p_NumDF number) return INTEGER
IS
    numEns1 NUMBER;
    numEns2 NUMBER;
    CURSOR curseur1 IS SELECT NumEnsGauche FROM DFS WHERE NUMDF=p_NumDF;
    CURSOR curseur2 IS SELECT NumEnsDroit FROM DFS WHERE NUMDF=p_NumDF;
BEGIN
    FOR ligne IN curseur1 LOOP
        numEns1 := ligne.NumEnsGauche;
    END LOOP;
    
    FOR ligne IN curseur2 LOOP
        numEns2 := ligne.NumEnsDroit;
    END LOOP;
    
    return EstInclus(numEns2, numEns1);
END EstTriviale;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EstPlusForte(p_NumDF1 number, p_NumDF2 number) return INTEGER
IS
    bool1 NUMBER;
    bool2 NUMBER;
    bool3 NUMBER;
    numEns1 NUMBER;
    numEns2 NUMBER;
    numEns3 NUMBER;
    numEns4 NUMBER;
    CURSOR curseur1 IS SELECT NumEnsGauche FROM DFS WHERE NUMDF=p_NumDF1;
    CURSOR curseur2 IS SELECT NumEnsDroit FROM DFS WHERE NUMDF=p_NumDF1;
    CURSOR curseur3 IS SELECT NumEnsGauche FROM DFS WHERE NUMDF=p_NumDF2;
    CURSOR curseur4 IS SELECT NumEnsDroit FROM DFS WHERE NUMDF=p_NumDF2;
BEGIN
    bool3 :=0;
    
    FOR ligne IN curseur1 LOOP
        numEns1 := ligne.NumEnsGauche;
    END LOOP;
    
    FOR ligne IN curseur2 LOOP
        numEns2 := ligne.NumEnsDroit;
    END LOOP;
    
    FOR ligne IN curseur3 LOOP
        numEns3 := ligne.NumEnsGauche;
    END LOOP;
    
    FOR ligne IN curseur4 LOOP
        numEns4 := ligne.NumEnsDroit;
    END LOOP;
    
    bool1 := EstInclus(numEns4, numEns2);
    bool2 := EstInclus(numEns1, numEns3);
    
    IF bool1=1 AND bool2=1 THEN
        bool3 := 1;
    END IF;
    
    return bool3;    
END EstPlusForte;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION CreerEnsDFVide return NUMBER
IS
    var NUMBER;
BEGIN
    var := NumEnsDF.NextVal;
    INSERT INTO EnsembleDFS VALUES(var);
    RETURN var;
END CreerEnsDFVide;
/

/******************************************************************/

CREATE OR REPLACE PROCEDURE AjouterDF(p_NumDF number, p_NumEnsDF number)
IS
BEGIN
    INSERT INTO ENSEMBLECONTIENTDF(NumDF,NumEnsDF) VALUES(p_NumDF,p_NumensDF);
END AjouterDF;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION CreerEnsDF(p_ChaineDF varchar) return NUMBER
IS
    position NUMBER;
    chaineDF1 VARCHAR(4000);
    chaineDF2 VARCHAR(4000);
    numDF1 NUMBER;
    numDF2 NUMBER;
    numEnsDF NUMBER;
BEGIN
    numEnsDF :=CreerEnsDFVide;
    position := INSTR(p_ChaineDF, ';',1);
    dbms_output.put_line(position);
    chaineDF1 := SUBSTR(p_ChaineDF, 1, position-1);
    dbms_output.put_line(chaineDF1);
    chaineDF2 := SUBSTR(p_ChaineDF, (length(chaineDF1)+2), (length(p_ChaineDF)-(length(chaineDF1))));
    dbms_output.put_line(chaineDF2);
    
    numDF1 := CreerDF(chaineDF1);
    numDF2 := CreerDF(chaineDF2);
    
    AjouterDF(numDF1, numEnsDF);
    AjouterDF(numDF2, numEnsDF);
    
    return NumEnsDF;
END CreerEnsDF;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EnsDF2Chaine(p_NumEnsDF number) return varchar
IS
    CURSOR curseur IS SELECT NumDF FROM EnsembleContientDF WHERE NumEnsDF=p_NumEnsDF ORDER BY NumDF;
    chaine varchar(4000);
BEGIN
    FOR ligne IN curseur LOOP
        chaine := chaine || DF2Chaine(ligne.NumDF) || ';';
    END LOOP;    
    chaine := SUBSTR(chaine, 1, length(chaine)-1);
     
    return chaine;
END EnsDF2Chaine;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION EnsDF2EnsAtt(p_NumEnsDF number) return NUMBER
IS
    CURSOR curseur IS SELECT NumDF FROM EnsembleContientDF WHERE NumEnsDF=p_NumEnsDF ORDER BY NumDF;
    chaine VARCHAR(4000);
    NumEns NUMBER;
BEGIN
    FOR ligne IN curseur LOOP
        chaine := chaine || DF2ChaineVirgule(ligne.NumDF) || ',';
    END LOOP;    
    chaine := SUBSTR(chaine, 1, length(chaine)-1);
    
    NumEns := CreerEnsAtt(chaine);
     
    return NumEns;
END EnsDF2EnsAtt;
/

/******************************************************************/

CREATE OR REPLACE FUNCTION CopieEnsDF(p_NumEnsDF number) return NUMBER
IS
    NumNewEns NUMBER;
    CURSOR curseur IS SELECT NumDF FROM EnsembleContientDF WHERE NumEnsDF=p_NumEnsDF;
BEGIN
    NumNewEns := CreerEnsDFVide();
    FOR ligne IN curseur LOOP
        AjouterAtt(ligne.NumDF, NumNewEns);
        dbms_output.put_line(ligne.NumDF);
    END LOOP;  
    
    return NumNewEns;
END CopieEnsDF;

/******************************************************************/
/**************************Execution*******************************/
/******************************************************************/


Execute AjouterAtt('PARIS',2);

DECLARE V NUMBER;
BEGIN
V := CreerEnsAtt('SALUT,COUCOU');
END;

SELECT EnsAtt2Chaine(6) from dual;
SELECT EstElement('AZERTY',4) from dual;
SELECT EstInclus(21,22) from dual;
SELECT EstEgal(21,24) from dual;

DECLARE V NUMBER;
BEGIN
V := UnionAtt(21,22);
END;

DECLARE V NUMBER;
BEGIN
V := IntersectionAtt(22,24);
END;

DECLARE V NUMBER;
BEGIN
V := SoustractionAtt(24,25);
END;

DECLARE V NUMBER;
BEGIN
V := CopieAtt(4);
END;

DECLARE V NUMBER;
BEGIN
V := CreerDF('Madrid,Pirate,Bateau,Couscous->Couscous,Madrid,Bateau');
END;

DECLARE V NUMBER;
BEGIN
V := CreerDF('Madrid,Pirate,Bateau,Couscous,Avion,Camion->Madrid,Bateau');
END;

SELECT DF2Chaine(16) from dual;

SELECT EstTriviale(16) from dual;

SELECT EstPlusForte(16,17) from dual;

DECLARE V NUMBER;
BEGIN
V := CreerEnsDFVide;
END;

Execute AjouterDF(15,1);

DECLARE V NUMBER;
BEGIN
V := CreerEnsDF('Maurice->Marion;Jean->Pascal');
END;

SELECT EnsDF2Chaine(3) from dual;

SELECT EnsDF2EnsAtt(3) from dual;

DECLARE V NUMBER;
BEGIN
V := EnsDF2EnsAtt(3);
END;

DECLARE V NUMBER;
BEGIN
V := CopieEnsDF(3);
END;
