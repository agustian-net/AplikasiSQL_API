package vsga.mobile.aplikasicrud_api;

public class Configuration {
    //Memanggil file PHP

    public static final String URL_ADD = "https://192.168.1.8/apiandroid/tambahpgw.php";
    public static final String URL_GET_ALL = "https://192.168.1.8/apiandroid/tampilsemuapgw.php";
    public static final String URL_GET_EMP = "https://192.168.1.8/apiandroid/tampilpgw.php?id=";
    public static final String URL_UPDATE_EMP = "https://192.168.1.8/apiandroid/updatepgw.php";
    public static final String URL_DELETE_EMP = "https://192.168.1.8/apiandroid/hapuspgw.php?=";

    //perintah untuk mengirimkan permintaan ke dalam skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAMA = "name";
    public static final String KEY_EMP_POSISI = "posisi";
    public static final String KEY_EMP_GAJI= "gaji";

    //Tags JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_POSISI = "posisi";
    public static final String TAG_GAJI = "gaji";

    //ID karyawan
    //emp merupakan singkatan dari Employee
    public static final String EMP_ID = "emp_id";

}
