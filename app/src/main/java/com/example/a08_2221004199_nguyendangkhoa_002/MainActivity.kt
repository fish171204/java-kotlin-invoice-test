package com.example.a08_2221004199_nguyendangkhoa_002

import android.app.DatePickerDialog
import android.icu.text.NumberFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a08_2221004199_nguyendangkhoa_002.Adapter.HoaDonAdapter
import com.example.a08_2221004199_nguyendangkhoa_002.DAO.HoaDonDAO
import com.example.a08_2221004199_nguyendangkhoa_002.Database.DatabaseHelper.Companion.TABLE_SANPHAM
import com.example.a08_2221004199_nguyendangkhoa_002.Source.HoaDon
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hoaDonAdapter: HoaDonAdapter
    private lateinit var hoaDonDAO: HoaDonDAO
    private lateinit var btnThem: MaterialButton
    private lateinit var btnXoa: MaterialButton
    private lateinit var btnSua: MaterialButton
    private lateinit var btnThoat: MaterialButton
    private lateinit var edtMaHD: TextInputEditText
    private lateinit var edtHoTenKH: TextInputEditText
    private lateinit var edtSoLuong: TextInputEditText
    private lateinit var edtNgayLap: TextInputEditText
    private lateinit var edtThanhToan: TextInputEditText


    private var selectedHoaDon: HoaDon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerNhanVien: MaterialAutoCompleteTextView = findViewById(R.id.spinnerNhanVien)
        val spinnerSanPham: MaterialAutoCompleteTextView = findViewById(R.id.spinnerSanPham)
        val spinnerTinhTrangGiao: MaterialAutoCompleteTextView = findViewById(R.id.spinnerTinhTrangGiao)



        val sanphamList = listOf(
            "Laptop Acer",
            "Laptop Dell",
            "Macbook",
            "Imac",
            "SamSung J3",
            "SamSung J4",
            "SamSung J5",
            "Iphone 10",
            "Iphone 11",
            "Iphone 12",
            "Iphone 13",
            "Iphone 14",
            "Iphone 15"
        )
        val sanphamAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sanphamList)
        spinnerSanPham.setAdapter(sanphamAdapter)

        val nhanVienList = listOf(
            "Nguyễn Đăng Khoa",
            "Nguyễn Kim Dũng",
            "Trần Minh Tú",
            "Nguyễn Trần Đoan Thi",
            "Nguyễn Thế Duy",
            "Nguyễn Trí Khang",
            "Ngọc Quang Huy",
            "Nguyễn Duy Lâm",
            "Trần Minh Tùng",
            "Cao Công Lợi",
            "Nguyễn Phước Thịnh",
            "Cao Tuấn Kiệt",
            "Cao Đăng Khoa"
        )
        val nhanVienAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nhanVienList)
        spinnerNhanVien.setAdapter(nhanVienAdapter)

        val tintranggiaoList =
            listOf("Chờ xử lý", "Đang giao", "Đã giao")
        val tintranggiaoAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tintranggiaoList)
        spinnerTinhTrangGiao.setAdapter(tintranggiaoAdapter)




        edtMaHD = findViewById(R.id.edtMaHD)
        edtHoTenKH = findViewById(R.id.edtHoTenKH)
        edtNgayLap = findViewById(R.id.edtNgayLapHD)
        edtSoLuong = findViewById(R.id.edtSoLuong)
        edtThanhToan = findViewById(R.id.edtThanhToan)

        btnThem = findViewById(R.id.btnThem)
        btnSua = findViewById(R.id.btnSua)
        btnXoa = findViewById(R.id.btnXoa)
        btnThoat = findViewById(R.id.btnThoat)


        hoaDonDAO = HoaDonDAO(this)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        hoaDonAdapter = HoaDonAdapter(emptyList()) { hoadon ->
            selectedHoaDon = hoadon
            Toast.makeText(this, "Đã chọn hóa đơn của khách hàng: ${hoadon.tenKH}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = hoaDonAdapter



        edtNgayLap.setOnClickListener {
            showDatePickerDialog { date -> edtNgayLap.setText(date) }
        }


        btnThem.setOnClickListener {
            val maHD = edtMaHD.text?.toString()?.trim()
            val hoTenKH = edtHoTenKH.text?.toString()?.trim()
            val ngayLap = edtNgayLap.text?.toString()?.trim()
            val soLuong = edtSoLuong.text.toString().toDoubleOrNull() ?: 0.0
            val thanhToan = edtThanhToan.text.toString().toDoubleOrNull()?.toLong() ?: 0L
            val nameNV = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerNhanVien).text?.toString()?.trim()
            val nameSP = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerSanPham).text?.toString()?.trim()
            val nameTT = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerTinhTrangGiao).text?.toString()?.trim()

            if (maHD.isNullOrEmpty() || hoTenKH.isNullOrEmpty() || ngayLap.isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin hóa đơn!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hoaDon = HoaDon(
                maHD = maHD.toIntOrNull() ?: 0,
                maNV = nameNV ?: "",
                tenKH = hoTenKH,
                SanPham = nameSP ?: "",
                soLuong = soLuong,
                ngayDat = ngayLap,
                thanhToan = thanhToan,
                tinhTrangGiao = nameTT ?: ""
            )

            if (hoaDonDAO.insertHoaDon(hoaDon)) {
                Toast.makeText(this, "Thêm hóa đơn thành công!", Toast.LENGTH_SHORT).show()
                loadHoaDonList()
                XoaTatCaField()
            } else {
                Toast.makeText(this, "Thêm hóa đơn thất bại!", Toast.LENGTH_SHORT).show()
            }
        }



        btnSua.setOnClickListener {
            selectedHoaDon?.let { hoaDon ->

                val updatedHoaDon = HoaDon(
                    maHD = hoaDon.maHD,
                    ngayDat = edtNgayLap.text?.toString()?.trim() ?: "",
                    maNV = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerNhanVien).text?.toString()?.trim() ?: "",
                    tinhTrangGiao = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerTinhTrangGiao).text?.toString()?.trim() ?: "",
                    SanPham = findViewById<MaterialAutoCompleteTextView>(R.id.spinnerSanPham).text?.toString()?.trim() ?: "",
                    tenKH = edtHoTenKH.text?.toString()?.trim() ?: "",
                    thanhToan = edtThanhToan.text.toString().toDoubleOrNull()?.toLong() ?: 0L,
                    soLuong = edtSoLuong.text.toString().toDoubleOrNull() ?: 0.0
                )

                if (updatedHoaDon.tenKH.isEmpty() || updatedHoaDon.ngayDat.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (updatedHoaDon.thanhToan <= 0) {
                    Toast.makeText(this, "Thanh toán phải lớn hơn 0!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                if (hoaDonDAO.updateHoaDon(updatedHoaDon)) {
                    Toast.makeText(this, "Cập nhật hóa đơn thành công!", Toast.LENGTH_SHORT).show()
                    loadHoaDonList()
                    XoaTatCaField()
                } else {
                    Toast.makeText(this, "Cập nhật thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show()
                }
            } ?: Toast.makeText(this, "Chưa chọn hóa đơn để sửa!", Toast.LENGTH_SHORT).show()
        }



        btnXoa.setOnClickListener {
            selectedHoaDon?.let { deleteHoaDon(it) } ?:
            Toast.makeText(this, "Chưa chọn hóa đơn để xóa!", Toast.LENGTH_SHORT).show()
        }

        btnThoat.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Xác nhận thoát")
                .setMessage("Bạn có muốn thoát khỏi chương trình?")
                .setPositiveButton("Có") { _, _ ->
                    finish()
                }
                .setNegativeButton("Không", null)
                .show()
        }

        loadHoaDonList()
    }






    private fun XoaTatCaField() {
        edtMaHD.setText("")
        edtHoTenKH.setText("")
        edtNgayLap.setText("")
        edtSoLuong.setText("")
        edtThanhToan.setText("")
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerNhanVien).setText("", false)
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerSanPham).setText("", false)
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerTinhTrangGiao).setText("", false)

        selectedHoaDon = null
    }

    private fun loadHoaDonList() {
        val hoadonList = hoaDonDAO.getAllHoaDon()
        hoaDonAdapter = HoaDonAdapter(hoadonList) { hoadon ->
            selectedHoaDon = hoadon
            fillHoaDonData(hoadon)
            Toast.makeText(this, "Đã chọn hóa đơn của khách hàng: ${hoadon.tenKH}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = hoaDonAdapter
        hoaDonAdapter.notifyDataSetChanged()
    }

    private fun fillHoaDonData(hoadon: HoaDon) {
        edtMaHD.setText(hoadon.maHD?.toString() ?: "")
        edtHoTenKH.setText(hoadon.tenKH)
        edtNgayLap.setText(hoadon.ngayDat)
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerNhanVien).setText(
            hoadon.maNV,
            false
        )
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerSanPham).setText(
            hoadon.SanPham,
            false
        )
        findViewById<MaterialAutoCompleteTextView>(R.id.spinnerTinhTrangGiao).setText(
            hoadon.tinhTrangGiao,
            false
        )
        edtThanhToan.setText(hoadon.thanhToan.toString())
        edtSoLuong.setText(hoadon.soLuong.toString())
    }


    private fun deleteHoaDon(hoadon: HoaDon) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc muốn xóa hóa đơn của khách hàng:  ${hoadon.tenKH} không?")
            .setPositiveButton("Xóa") { _, _ ->
                if (hoaDonDAO.deleteHoaDon(hoadon.maHD)) {
                    Toast.makeText(this, "Xóa nhân viên thành công!", Toast.LENGTH_SHORT).show()
                    loadHoaDonList()
                } else {
                    Toast.makeText(this, "Không tìm thấy nhân viên để xóa!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }


    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate =
                    String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                onDateSelected(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

}

