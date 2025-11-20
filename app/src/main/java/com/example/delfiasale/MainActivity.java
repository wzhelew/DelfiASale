package com.example.delfiasale;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delfiasale.data.api.ApiClient;
import com.example.delfiasale.data.api.ApiService;
import com.example.delfiasale.data.model.GroupDto;
import com.example.delfiasale.data.model.ItemDto;
import com.example.delfiasale.data.model.SaleItem;
import com.example.delfiasale.data.model.SaleRequest;
import com.example.delfiasale.ui.adapter.GroupAdapter;
import com.example.delfiasale.ui.adapter.ProductAdapter;
import com.example.delfiasale.ui.adapter.ReceiptAdapter;
import com.example.delfiasale.ui.model.ReceiptLine;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private GroupAdapter groupAdapter;
    private ProductAdapter productAdapter;
    private ReceiptAdapter receiptAdapter;
    private final List<ReceiptLine> receiptLines = new ArrayList<>();
    private TextView totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getInstance();

        RecyclerView groupRecycler = findViewById(R.id.groupRecycler);
        RecyclerView productRecycler = findViewById(R.id.productRecycler);
        RecyclerView receiptRecycler = findViewById(R.id.receiptRecycler);
        totalAmount = findViewById(R.id.totalAmount);
        Button sendSale = findViewById(R.id.sendSale);

        groupAdapter = new GroupAdapter(new ArrayList<>(), this::loadProducts);
        productAdapter = new ProductAdapter(new ArrayList<>(), this::addProductToReceipt);
        receiptAdapter = new ReceiptAdapter(receiptLines);

        groupRecycler.setLayoutManager(new LinearLayoutManager(this));
        productRecycler.setLayoutManager(new LinearLayoutManager(this));
        receiptRecycler.setLayoutManager(new LinearLayoutManager(this));

        groupRecycler.setAdapter(groupAdapter);
        productRecycler.setAdapter(productAdapter);
        receiptRecycler.setAdapter(receiptAdapter);

        sendSale.setOnClickListener(v -> sendSale());

        fetchGroups();
    }

    private void fetchGroups() {
        apiService.getGroups().enqueue(new Callback<List<GroupDto>>() {
            @Override
            public void onResponse(Call<List<GroupDto>> call, Response<List<GroupDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    groupAdapter.updateData(response.body());
                    if (!response.body().isEmpty()) {
                        loadProducts(response.body().get(0));
                    }
                } else {
                    showError("Грешка при зареждане на групите");
                }
            }

            @Override
            public void onFailure(Call<List<GroupDto>> call, Throwable t) {
                showError("Неуспешна връзка с REST API");
            }
        });
    }

    private void loadProducts(GroupDto group) {
        apiService.getItemsByGroup(group.getId()).enqueue(new Callback<List<ItemDto>>() {
            @Override
            public void onResponse(Call<List<ItemDto>> call, Response<List<ItemDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productAdapter.updateData(response.body());
                } else {
                    showError("Грешка при зареждане на стоките");
                }
            }

            @Override
            public void onFailure(Call<List<ItemDto>> call, Throwable t) {
                showError("Неуспешна връзка с REST API");
            }
        });
    }

    private void addProductToReceipt(ItemDto item) {
        for (ReceiptLine line : receiptLines) {
            if (line.getBarcode().equals(item.getBarcode())) {
                line.incrementQuantity();
                updateTotal();
                receiptAdapter.notifyDataSetChanged();
                return;
            }
        }
        ReceiptLine line = new ReceiptLine(item.getName(), item.getBarcode(), item.getPrice());
        receiptLines.add(line);
        receiptAdapter.notifyDataSetChanged();
        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for (ReceiptLine line : receiptLines) {
            total += line.getTotal();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("bg", "BG"));
        totalAmount.setText(formatter.format(total));
    }

    private void sendSale() {
        if (receiptLines.isEmpty()) {
            Toast.makeText(this, R.string.empty_receipt, Toast.LENGTH_SHORT).show();
            return;
        }

        List<SaleItem> saleItems = new ArrayList<>();
        for (ReceiptLine line : receiptLines) {
            saleItems.add(new SaleItem(line.getBarcode(), line.getQuantity(), line.getUnitPrice()));
        }

        apiService.submitSale(new SaleRequest(saleItems)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    receiptLines.clear();
                    receiptAdapter.notifyDataSetChanged();
                    updateTotal();
                    Toast.makeText(MainActivity.this, "Продажбата е изпратена", Toast.LENGTH_SHORT).show();
                } else {
                    showError("Грешка при изпращане на продажбата");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showError("Неуспешна връзка с REST API");
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
