# Training ISO8583 dengan JPos

## Deployment Aplikasi Backend

* URL untuk lihat daftar rekening : https://pelatihan-backend.herokuapp.com/api/rekening/
* URL untuk lihat mutasi rekening : https://pelatihan-backend.herokuapp.com/api/rekening/001/mutasi/
* URL untuk posting mutasi (gunakan method POST) : https://pelatihan-backend.herokuapp.com/api/rekening/001/

Contoh data untuk posting transfer ke rekening bank lain :

```json
  {
    "rekening" : {
      "id" : "001"
    },
    "waktuTransaksi" : "2016-11-03T17:39:44",
    "nilai" : -75000.00,
    "keterangan" : "Transfer"
  } 
```

Contoh data untuk posting topup dari rekening bank lain :


```json
  {
    "rekening" : {
      "id" : "001"
    },
    "waktuTransaksi" : "2016-11-04T17:39:44",
    "nilai" : 50000.00,
    "keterangan" : "Topup"
  } 
```

