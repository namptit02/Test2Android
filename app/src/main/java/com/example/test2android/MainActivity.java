package com.example.test2android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2android.model.WorkItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etTenCongViec;
    private EditText etNoiDungCongViec;
    private RadioGroup rgRadioGroup;
    private EditText etDate;
    private Button btnAdd, btnUpdate;
    private RadioButton rbNam;
    private RadioButton rbNu;
    private RecyclerView rcvListWorks;
    private WorkAdapter workAdapter;
    private List<WorkItem> workList;
    private String selectedDate = ""; // Biến để lưu trữ ngày được chọn


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etTenCongViec = findViewById(R.id.etTenCongViec);
        etNoiDungCongViec = findViewById(R.id.etNoiDungCongViec);
        rgRadioGroup = findViewById(R.id.rgRadioGroup);
        etDate = findViewById(R.id.etNgayHoanThanh);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnAdd = findViewById(R.id.btnAdd);
        rcvListWorks = findViewById(R.id.rcvListWorks);

        workList = new ArrayList<>();
        workAdapter = new WorkAdapter(workList);
        rbNam = findViewById(R.id.rbNam);
        rbNu = findViewById(R.id.rbNu);
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
                    // Kiểm tra xem tên công việc đã tồn tại trong RecyclerView hay chưa
                    if (isNameExist(name)) {
                        Toast.makeText(MainActivity.this, "Tên công việc đã tồn tại.", Toast.LENGTH_SHORT).show();
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
                        etDate.setText("");
                    }
                }
            }
        });

        findViewById(R.id.btnNgayHoanThanh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        workAdapter.setOnItemClickListener(new WorkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WorkItem workItem) {
                // Cập nhật các EditText, RadioButton và TextView với thông tin mục đã chọn
                etTenCongViec.setText(workItem.getName());
                etNoiDungCongViec.setText(workItem.getTitle());
                if (workItem.getGenderIcon() == R.drawable.male) {
                    rbNam.setChecked(true);
                    rbNu.setChecked(false);
                } else {
                    rbNam.setChecked(false);
                    rbNu.setChecked(true);
                }
                etDate.setText(workItem.getDate());
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các EditText, RadioButton, và TextView sau khi chỉnh sửa
                String updatedName = etTenCongViec.getText().toString();
                String updatedTitle = etNoiDungCongViec.getText().toString();
                String updatedGender = rgRadioGroup.getCheckedRadioButtonId() == R.id.rbNam ? "Man" : "Woman";
                String updatedDate = etDate.getText().toString();

                // Kiểm tra xem có thông tin cần thiết đã được nhập chưa
                if (updatedName.isEmpty() || updatedTitle.isEmpty() || updatedGender.isEmpty() || updatedDate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo một đối tượng WorkItem đã chỉnh sửa
                    int updatedGenderIcon = (updatedGender.equals("Man")) ? R.drawable.male : R.drawable.female;
                    WorkItem updatedWorkItem = new WorkItem(updatedGenderIcon, updatedName, updatedTitle, updatedDate);

                    // Cập nhật thông tin mục đã chọn trong WorkAdapter
                    workAdapter.updateSelectedWorkItem(updatedWorkItem);

                    // Đặt lại các trường input cho công việc tiếp theo (name, title, date)
                    etTenCongViec.setText("");
                    etNoiDungCongViec.setText("");
                    rgRadioGroup.clearCheck();
                    etDate.setText("");
                    selectedDate = ""; // Đặt lại ngày đã chọn
                }
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

    private boolean isNameExist(String name) {
        for (WorkItem item : workList) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


}
