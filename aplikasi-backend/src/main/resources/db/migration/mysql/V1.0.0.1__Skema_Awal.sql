create table rekening (
    id VARCHAR(36),
    nomor VARCHAR(10) NOT NULL,
    nama VARCHAR(255) NOT NULL,
    saldo NUMERIC(19,2),
    PRIMARY KEY (id),
    UNIQUE(nomor)
) Engine=InnoDB ;

create table mutasi (
    id VARCHAR(36),
    id_rekening VARCHAR(36),
    waktu_transaksi TIMESTAMP NOT NULL,
    nilai NUMERIC(19,2) NOT NULL,
    keterangan VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_rekening) REFERENCES rekening(id)
) Engine=InnoDB ;