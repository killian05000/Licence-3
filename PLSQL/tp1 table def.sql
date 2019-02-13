create table ensembleAttributs
(
    NumEnsAtt NUMBER PRIMARY KEY
);

create table EnsembleContientAttribut
(
    NumEnsAtt NUMBER NOT NULL,
    NomAtt VARCHAR(50) NOT NULL,
    CONSTRAINT PK_EnsembleContientAttribut PRIMARY KEY (NumEnsAtt, NomAtt),
    CONSTRAINT FK_NUMENSATT FOREIGN KEY (NumEnsAtt) REFERENCES ensembleAttributs(NumEnsAtt) on delete cascade
);

create table DFs
(
    NumDF NUMBER PRIMARY KEY,
    NumEnsGauche NUMBER NOT NULL,
    NumEnsDroit NUMBER NOT NULL,
    CONSTRAINT UNIQUE_DFS UNIQUE (NumEnsGauche, NumEnsDroit),
    CONSTRAINT FK_NumEnsGauche FOREIGN KEY (NumEnsGauche) REFERENCES ensembleAttributs(NumEnsAtt),
    CONSTRAINT FK_NumEnsDroit FOREIGN KEY (NumEnsDroit) REFERENCES ensembleAttributs(NumEnsAtt)
);

create sequence NumDF;
create sequence NumEnsAtt;

--drop table ensembleAttributs;
--drop table EnsembleContientAttribut;
--drop sequence NumEnsAtt;

--SELECT * FROM ensembleAttributs;