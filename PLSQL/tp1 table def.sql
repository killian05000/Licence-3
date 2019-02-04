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

create sequence NumEnsAtt;

--drop table ensembleAttributs;
--drop table EnsembleContientAttribut;
--drop sequence NumEnsAtt;

--SELECT * FROM ensembleAttributs;