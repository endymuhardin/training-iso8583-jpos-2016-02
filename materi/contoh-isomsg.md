# Test ISO Message #

Cara test : 

1. Jalankan Aplikasi Jpos

    mvn clean package exec:java -Dexec.mainClass=com.muhardin.endy.belajar.jpos.AplikasiJpos

2. Telnet ke localhost port 10000

    telnet localhost 10000
    Trying ::1...
    Connected to localhost.
    Escape character is '^]'.

3. Copy paste request message, kemudian tekan enter
4. Amati log aplikasi

## Berbagai Contoh Message ##

* Network Management : Echo Request

```
00690800C220000080000000010000000000000003123111514440012345603123301
```

* Account Inquiry Request

```
01040200E23A400A000000000000000004000000030013410001115112500123456112500111511156012C00000000C0000000003001
```