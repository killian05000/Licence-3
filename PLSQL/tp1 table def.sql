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

create table ensembleDFs
(
    NumEnsDF NUMBER PRIMARY KEY
);

create table ensembleContientDF
(
    NumEnsDF NUMBER,
    NumDF NUMBER,
    CONSTRAINT PK_ensembleContientDF PRIMARY KEY (NumEnsDF, NumDF),
    CONSTRAINT FK_NumEnsDF FOREIGN KEY (NumEnsDF) REFERENCES ensembleDFs(NumEnsDF) on delete cascade,
    CONSTRAINT FK_NumDF FOREIGN KEY (NumDF) REFERENCES DFs (NumDF)
);


create sequence NumDF;
create sequence NumEnsAtt;
create sequence NumEnsDF;

--drop table ensembleAttributs;
--drop table EnsembleContientAttribut;
--drop sequence NumEnsAtt;
--drop table ensembleContientDF;

--SELECT * FROM ensembleAttributs;