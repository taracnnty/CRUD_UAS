public class Apotek {

    private String Kode = null;
    private String Nama = null;
    private String Harga = null;

    public Apotek(String Kode, String Nama, String Harga) {
        this.Kode = Kode;
        this.Nama = Nama;
        this.Harga = Harga;
    }

    public String getKode() {
        return Kode;
    }

    public String getNama() {
        return Nama;
    }

    public String getHarga() {
        return Harga;
    }
}