package com.example.daisyapp.data.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.daisyapp.R
import com.example.daisyapp.data.model.CancerHistory
import com.example.daisyapp.databinding.ItemHistoryBinding
import com.example.daisyapp.view.viewmodel.model.HistoryViewModel

class HistoryAdapter(
    private val listCancerHistory: MutableList<CancerHistory>,
    private val context: Context,
    private val viewModel: HistoryViewModel // Tambahkan ViewModel
) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listCancerHistory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cancerHistory = listCancerHistory[position]

        with(holder.binding) {
            ivHistory.load(cancerHistory.image) {
                placeholder(ColorDrawable(Color.LTGRAY))
            }
            tvHistoryResult.text = context.getString(
                R.string.cancer_history_label,
                cancerHistory.label
            )

            tvHistorySuggestion.text = if (cancerHistory.label == "Normal") {
                // Jika prediksi adalah "Normal"
                context.getString(R.string.normal_prediction_message)
            } else {
                // Jika prediksi bukan "Normal"
                context.getString(R.string.see_doctor_immediately)
            }


            btnDelete.setOnClickListener {
                showPopupDelete(position) // Kirim posisi item yang akan dihapus
            }
        }
    }

    private fun showPopupDelete(position: Int) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.pop_up_delete_dialog) // Pastikan layout ini benar
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnConfirmDelete = dialog.findViewById<Button>(R.id.btn_delete)

        btnCancel.setOnClickListener {
            dialog.dismiss() // Tutup dialog jika pengguna membatalkan
        }

        btnConfirmDelete.setOnClickListener {
            // Hapus item dari list dan notify adapter
            val removedItem = listCancerHistory.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listCancerHistory.size)

            // Panggil ViewModel untuk menghapus dari database
            viewModel.deleteResultById(removedItem.id)

            dialog.dismiss() // Tutup dialog setelah menghapus
        }

        dialog.show()
    }
}
