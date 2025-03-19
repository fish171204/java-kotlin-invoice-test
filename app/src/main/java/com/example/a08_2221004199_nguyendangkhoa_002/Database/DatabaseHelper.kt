package com.example.a08_2221004199_nguyendangkhoa_002.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "QLDonHang.db"
        const val DATABASE_VERSION = 1

        const val TABLE_HOADON = "HOADON"
        const val COLUMN_MAHD = "MAHD"
        const val COLUMN_NHANVIEN = "MANV"
        const val COLUMN_TENKH = "TENKH"
        const val COLUMN_SANPHAM = "SANPHAM"
        const val COLUMN_SOLUONG = "SOLUONG"
        const val COLUMN_NGAYDAT = "NGAYDAT"
        const val COLUMN_THANHTOAN = "THANHTOAN"
        const val COLUMN_TINHTRANGGIAO = "TINHTRANGGIAO"


        const val TABLE_SANPHAM = "SANPHAM"
        const val COLUMN_MASP = "MASP"
        const val COLUMN_TENSP = "TENSP"
        const val COLUMN_DONGIA = "DONGIA"
        const val COLUMN_SOLUONGTON = "SOLUONGTON"

        const val TABLE_NHANVIEN = "NHANVIEN"
        const val COLUMN_MANV = "MANV"
        const val COLUMN_TENNV= "TENNV"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSanPham = """
            CREATE TABLE $TABLE_SANPHAM(
            $COLUMN_MASP TEXT PRIMARY KEY,
            $COLUMN_TENSP TEXT NOT NULL,       
            $COLUMN_DONGIA REAL NOT NULL,    
            $COLUMN_SOLUONGTON REAL NOT NULL
            )
            """.trimIndent()

        val createTableNhanVien = """
            CREATE TABLE $TABLE_NHANVIEN(
            $COLUMN_MANV TEXT PRIMARY KEY,
            $COLUMN_TENNV TEXT NOT NULL         
            )
            """.trimIndent()

        val createTableHoaDon = """
            CREATE TABLE $TABLE_HOADON (
                $COLUMN_MAHD INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NHANVIEN TEXT NOT NULL,
                $COLUMN_TENKH TEXT NOT NULL,
                $COLUMN_SANPHAM TEXT NOT NULL,
                $COLUMN_NGAYDAT TEXT NOT NULL,
                $COLUMN_SOLUONG REAL NOT NULL,
                $COLUMN_THANHTOAN REAL NOT NULL,
                $COLUMN_TINHTRANGGIAO REAL NOT NULL,
                FOREIGN KEY ($COLUMN_SANPHAM) REFERENCES $TABLE_SANPHAM($COLUMN_MASP),
                FOREIGN KEY ($COLUMN_NHANVIEN) REFERENCES $TABLE_SANPHAM($COLUMN_MANV)
            )
            """.trimIndent()

        db.execSQL(createTableSanPham)
        db.execSQL(createTableNhanVien)
        db.execSQL(createTableHoaDon)

        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP01', 'Laptop Acer', '18000000', '159')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP02', 'Laptop Dell', '17500000', '125')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP03', 'Macbook', '36000000', '151')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP04', 'Imac', '39000000', '215')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP05', 'SamSung J3', '8500000', '128')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP06', 'SamSung J4', '8800000', '88')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP07', 'SamSung J5', '9000000', '99')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP08', 'Iphone 10', '18000000', '124')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP09', 'Iphone 11', '20000000', '156')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP10', 'Iphone 12', '24000000', '165')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP11', 'Iphone 13', '28000000', '132')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP12', 'Iphone 14', '31000000', '188')")
        db.execSQL("INSERT INTO $TABLE_SANPHAM VALUES ('SP13', 'Iphone 15', '36000000', '199')")

        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV01', 'Nguyễn Đăng Khoa')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV02', 'Nguyễn Kim Dũng')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV03', 'Trần Minh Tú')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV04', 'Nguyễn Trần Đoan Thi')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV05', 'Nguyễn Thế Duy')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV06', 'Nguyễn Trí Khang')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV07', 'Ngọc Quang Huy')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV08', 'Nguyễn Duy Lâm')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV09', 'Trần Minh Tùng')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV10', 'Cao Công Lợi')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV11', 'Nguyễn Phước Thịnh')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV12', 'Cao Tuấn Kiệt')")
        db.execSQL("INSERT INTO $TABLE_NHANVIEN VALUES ('NV13', 'Cao Đăng Khoa')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HOADON")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SANPHAM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NHANVIEN")
        onCreate(db)
    }
}