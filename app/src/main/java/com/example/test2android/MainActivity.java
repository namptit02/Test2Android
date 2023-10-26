package com.example.test2android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test2android.model.WorkItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etTenCongViec;
    private EditText etNoiDungCongViec;
    private RadioGroup rgRadioGroup;
    private RecyclerView rcvListWorks;
    private WorkAdapter workAdapter;
    private List<WorkItem> workList;
    private String selectedDate = ""; // Biến để lưu trữ ngày được chọn
    private WorkItem selectedWorkItem;
    private EditText etSelectedTenCongViec;
    private EditText etSelectedNoiDungCongViec;
    private RadioGroup rgSelectedRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSelectedTenCongViec = findViewById(R.id.etTenCongViec);
        etSelectedNoiDungCongViec = findViewById(R.id.etNoiDungCongViec);
        rgSelectedRadioGroup = findViewById(R.id.rgRadioGroup);

        etTenCongViec = findViewById(R.id.etTenCongViec);
        etNoiDungCongViec = findViewById(R.id.etNoiDungCongViec);
        rgRadioGroup = findViewById(R.id.rgRadioGroup);
        rcvListWorks = findViewById(R.id.rcvListWorks);

        workList = new ArrayList<>();
        workAdapter = new WorkAdapter(workList);

        rcvListWorks.setLayoutManager(new LinearLayoutManager(this));
        rcvListWorks.setAdapter(workAdapter);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etTenCongViec.getText().toString();
                String title = etNoiDungCongViec.getText().toString();
                String gender = rgRadioGroup.getCheckedRadioButtonId() == R.id.rbNam ? "Man" : "Woman";

                // Kiểm tra xem có thông tin cần thiết đã được nhập chưa
                if (name.isEmpty() || title.isEmpty() || gender.isEmpty() || selectedDate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    int genderIcon = (gender.equals("Man")) ? R.drawable.male : R.drawable.female;

                    WorkItem workItem = new WorkItem(genderIcon, name, title, selectedDate);
                    workList.add(workItem);
                    workAdapter.notifyDataSetChanged();

                    // Đặt lại các trường input cho công việc tiếp theo (name, title, date)
                    etTenCongViec.setText("");
                    etNoiDungCongViec.setText("");
                    rgRadioGroup.clearCheck();
                    selectedDate = ""; // Đặt lại ngày đã chọn
                }
            }
        });

        findViewById(R.id.btnNgayHoanThanh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
                        EditText etNgayHoanThanh = findViewById(R.id.etNgayHoanThanh);
                        etNgayHoanThanh.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }







}
