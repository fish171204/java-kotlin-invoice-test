package com.example.a08_2221004199_nguyendangkhoa_002.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.a08_2221004199_nguyendangkhoa_002.Database.DatabaseHelper
import com.example.a08_2221004199_nguyendangkhoa_002.Source.HoaDon

class HoaDonDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertHoaDon(hoadon: HoaDon): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NHANVIEN, hoadon.maNV)
            put(DatabaseHelper.COLUMN_TENKH, hoadon.tenKH)
            put(DatabaseHelper.COLUMN_SANPHAM, hoadon.SanPham)
            put(DatabaseHelper.COLUMN_SOLUONG, hoadon.soLuong)
            put(DatabaseHelper.COLUMN_NGAYDAT, hoadon.ngayDat)
            put(DatabaseHelper.COLUMN_THANHTOAN, hoadon.thanhToan)
            put(DatabaseHelper.COLUMN_TINHTRANGGIAO, hoadon.tinhTrangGiao)

        }
        val result = db.insert(DatabaseHelper.TABLE_HOADON, null, values)
        db.close()
        return result != -1L
    }

    fun getAllHoaDon(): List<HoaDon> {
        val hoadonList = mutableListOf<HoaDon>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_HOADON}", null)

        if (cursor.moveToFirst()) {
            do {
                val hoadon = HoaDon(
                    maHD = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MAHD)),
                    maNV = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NHANVIEN)),
                    tenKH = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TENKH)),
                    SanPham = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SANPHAM)),
                    soLuong = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SOLUONG)),
                    ngayDat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NGAYDAT)),
                    thanhToan = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THANHTOAN)),
                    tinhTrangGiao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TINHTRANGGIAO)),
                )
                hoadonList.add(hoadon)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return hoadonList
    }

    fun updateHoaDon(hoadon: HoaDon): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NHANVIEN, hoadon.maNV)
            put(DatabaseHelper.COLUMN_TENKH, hoadon.tenKH)
            put(DatabaseHelper.COLUMN_SANPHAM, hoadon.SanPham)
            put(DatabaseHelper.COLUMN_SOLUONG, hoadon.soLuong)
            put(DatabaseHelper.COLUMN_NGAYDAT, hoadon.ngayDat)
            put(DatabaseHelper.COLUMN_THANHTOAN, hoadon.thanhToan)
            put(DatabaseHelper.COLUMN_TINHTRANGGIAO, hoadon.tinhTrangGiao)
        }
        val result = db.update(
            DatabaseHelper.TABLE_HOADON,
            values,
            "${DatabaseHelper.COLUMN_MAHD} = ?",
            arrayOf(hoadon.maHD.toString())
        )
        db.close()
        return result > 0
    }


    fun deleteHoaDon(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete(
            DatabaseHelper.TABLE_HOADON,
            "${DatabaseHelper.COLUMN_MAHD}=?",
            arrayOf(id.toString())
        )
        db.close()
        return result > 0
    }
}
