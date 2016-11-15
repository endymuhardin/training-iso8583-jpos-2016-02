# Menggunakan HSM #

* HSM adalah singkatan dari Hardware Security Module
* HSM __bukan__ tempat penyimpanan key, dia hanya menyimpan satu key saja, yang disebut dengan Local Master Key (LMK)
* Semua key lain digenerate oleh HSM, kemudian diencrypt dengan LMK, baru diberikan ke kita. Key yang terencrypt dengan LMK ini yang kita simpan di database.
* Fitur terpenting HSM adalah anti-tampering. Dia bisa menghapus LMKnya bila terjadi pembongkaran paksa, sehingga LMK kita bisa disimpan dengan aman.

Beberapa istilah dalam penggunaan HSM :

* LMK : Local Master Key. Key utama yang digunakan untuk mengenkripsi key lain
* TMK : Terminal Master Key. Key di terminal (ATM atau EDC) yang digunakan untuk mengenkripsi key transaksi
* TPK : Terminal Pin Key. Key yang digunakan untuk mengenkripsi data transaksi (misalnya PIN nasabah)

Referensi :

* [HSM Basic](http://web.archive.org/web/20130405032855/http://jpos.org/wiki/HSM_basics)
* [HSM More](http://web.archive.org/web/20121225184341/http://jpos.org/wiki/HSM_basics_continued)

## Inisialisasi LMK ##

LMK diinisialisasi dengan prosedur Key Ceremony. Biasanya ada beberapa orang (Key Custodian) yang bergiliran memasukkan key. LMK nantinya akan dihasilkan dari gabungan key yang diinput oleh Key Custodian

[![Key Ceremony](img/keyceremony.jpg)](https://www.flickr.com/photos/endymuhardin/30879980942/in/dateposted/)

## Prosedur Key Change ##

Secara periodik, TPK di masing-masing terminal diganti baru untuk menghindari replay attack. Berikut adalah prosedur penggantian key.

[![Key Change](img/keychange.jpg)](https://www.flickr.com/photos/endymuhardin/30908461751/in/dateposted/)

## Prosedur Pencetakan PIN ATM (PIN Mailer) ##

[![Pin Mailer](img/pinmailer.jpg)](https://www.flickr.com/photos/endymuhardin/30879967322/in/dateposted/)

## Prosedur Ganti PIN dengan PIN Pad ##

[![Pin Pad](img/pinpad.jpg)](https://www.flickr.com/photos/endymuhardin/30908490491/in/dateposted/)

## Prosedur Verifikasi PIN dengan HSM pada waktu Tarik Tunai di ATM ##

[![PIN Verification](img/pinverification.jpg)](https://www.flickr.com/photos/endymuhardin/25360867809/in/dateposted/)

## Prosedur PIN Translation bila bertransaksi di ATM yang berbeda dengan penerbit kartu ##

[![PIN Translation](img/pintranslation.jpg)](https://www.flickr.com/photos/endymuhardin/30694924240/in/dateposted/)