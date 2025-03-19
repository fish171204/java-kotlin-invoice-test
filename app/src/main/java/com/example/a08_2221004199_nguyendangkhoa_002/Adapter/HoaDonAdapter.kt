package com.example.a08_2221004199_nguyendangkhoa_002.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a08_2221004199_nguyendangkhoa_002.R
import com.example.a08_2221004199_nguyendangkhoa_002.Source.HoaDon

class HoaDonAdapter(
    private var hoadonList: List<HoaDon>,
    private val onItemClick: (HoaDon) -> Unit
) : RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>() {


    fun setHoaDon(hoadon: List<HoaDon>) {
        this.hoadonList = hoadon
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoaDonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return HoaDonViewHolder(view)
    }


    override fun onBindViewHolder(holder: HoaDonViewHolder, position: Int) {
        val hoadon = hoadonList[position]
        holder.bind(hoadon)
        holder.itemView.setOnClickListener { onItemClick(hoadon) }


    }

    override fun getItemCount() = hoadonList.size
    class HoaDonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMaHD: TextView = view.findViewById(R.id.tv_MaHD)
        private val tvHoTenNV: TextView = view.findViewById(R.id.tv_nhan_vien)
        private val tvHoTenKH: TextView = view.findViewById(R.id.tv_khach_hang)
        private val tvTenSP: TextView = view.findViewById(R.id.tv_san_pham)
        private val tvSoLuong: TextView = view.findViewById(R.id.tv_so_luong)
        private val tvNgayDat: TextView = view.findViewById(R.id.tv_ngay_lap)
        private val tvThanhToan: TextView = view.findViewById(R.id.tv_thanh_toan)
        private val tvTinhTrangGiao: TextView = view.findViewById(R.id.tv_tinh_trang_giao)

        fun bind(hoadon: HoaDon) {
            tvMaHD.text = "Mã hóa đơn: ${hoadon.maHD}"
            tvHoTenNV.text = "Nhân viên: ${hoadon.maNV}"
            tvHoTenKH.text = "Khách hàng: ${hoadon.tenKH}"
            tvTenSP.text = "Sản phẩm: ${hoadon.SanPham}"
            tvSoLuong.text = "Số lượng: ${hoadon.soLuong}"
            tvNgayDat.text = "Ngày lập: ${hoadon.ngayDat}"
            tvThanhToan.text = "Thanh toán: ${hoadon.thanhToan} VNĐ " //
            tvTinhTrangGiao.text = "Tình trạng giao : ${hoadon.tinhTrangGiao} " //
        }
    }
}
