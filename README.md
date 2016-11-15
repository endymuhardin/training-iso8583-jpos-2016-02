# Training ISO8583 dengan JPos

## Deployment Aplikasi Backend

* URL untuk lihat daftar rekening : https://pelatihan-backend.herokuapp.com/api/rekening/
* URL untuk lihat mutasi rekening : https://pelatihan-backend.herokuapp.com/api/rekening/001/mutasi/
* URL untuk posting mutasi (gunakan method POST) : https://pelatihan-backend.herokuapp.com/api/rekening/001/

Contoh data untuk posting mutasi:

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

