package com.example.test2android;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2android.model.WorkItem;

import java.util.List;


public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {
    private WorkItem selectedWorkItem = null;
    private List<WorkItem> workList;


    public WorkAdapter(List<WorkItem> workList) {
        this.workList = workList;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work, parent, false);
        return new WorkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        WorkItem workItem = workList.get(position);
        holder.bind(workItem, selectedWorkItem);

        // Xử lý sự kiện nhấp vào một mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedWorkItem = workItem;
                itemClickListener.onItemClick(workItem);

            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = holder.getAdapterPosition();
                if (itemPosition != RecyclerView.NO_POSITION) {
                    workList.remove(itemPosition);
                    notifyItemRemoved(itemPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workList.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder {
        public void setSelectedPosition(int position) {

            notifyDataSetChanged();
        }
        private ImageView imageViewGender;
        private TextView textViewName;
        private TextView textViewTitle;
        private TextView textViewDate;
        private Button btnDelete; // Sửa lỗi chính tả


        public WorkViewHolder(View itemView) {
            super(itemView);
            imageViewGender = itemView.findViewById(R.id.imageViewGender);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            btnDelete = itemView.findViewById(R.id.btnDelete); //
        }

        public void bind(WorkItem workItem, WorkItem selectedWorkItem) {
            imageViewGender.setImageResource(workItem.getGenderIcon());
            textViewName.setText(workItem.getName());
            textViewTitle.setText(workItem.getTitle());
            textViewDate.setText(workItem.getDate());

            // Xác định xem mục này có được chọn hay không và cập nhật giao diện
            if (workItem == selectedWorkItem) {
                itemView.setBackgroundColor(Color.YELLOW); // Hoặc bạn có thể sử dụng màu khác để đánh dấu
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT); // Trạng thái bình thường
            }


        }
    }
    public interface OnItemClickListener {
        void onItemClick(WorkItem workItem);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public void updateSelectedWorkItem(WorkItem updatedWorkItem) {
        if (selectedWorkItem != null) {
            int index = workList.indexOf(selectedWorkItem);
            if (index != -1) {
                workList.set(index, updatedWorkItem);
                notifyItemChanged(index);
            }
        }
    }
    public void filterList(List<WorkItem> filteredList) {
        workList = filteredList;
        notifyDataSetChanged();
    }


}

