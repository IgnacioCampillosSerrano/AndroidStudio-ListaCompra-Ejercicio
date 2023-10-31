package ignacio.campillos.androidstudio_listacompra_ejercicio;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;
import java.util.ArrayList;

import ignacio.campillos.androidstudio_listacompra_ejercicio.adapaters.ProductsAdapter;
import ignacio.campillos.androidstudio_listacompra_ejercicio.databinding.ActivityMainBinding;
import ignacio.campillos.androidstudio_listacompra_ejercicio.modelos.Product;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> productList;
    private ActivityMainBinding binding;

    private ProductsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productList = new ArrayList<>();

        adapter = new ProductsAdapter(productList, R.layout.product_view_holder, MainActivity.this);
        layoutManager = new GridLayoutManager(this,3);

        binding.contentMain.container.setAdapter(adapter);
        binding.contentMain.container.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProduct().show();
            }
        });
    }

    private AlertDialog createProduct(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("CREATE PRODUCT");
        builder.setCancelable(false);

        View productViewModel = LayoutInflater.from(this).inflate(R.layout.product_view_model,null);

        EditText txtName = productViewModel.findViewById(R.id.txtNameProductViewModel);
        EditText txtQuantity = productViewModel.findViewById(R.id.txtQuantityProductViewModel);
        EditText txtPrice = productViewModel.findViewById(R.id.txtPriceProductViewModel);

        TextView lbTotal = productViewModel.findViewById(R.id.lbTotalProductViewModel);

        builder.setView(productViewModel);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int quantity = Integer.parseInt(txtQuantity.getText().toString());
                    float price = Float.parseFloat(txtPrice.getText().toString());
                    float total = quantity * price;

                    //Poner el formato de la moneda (Por defecto €)

                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                    lbTotal.setText(numberFormat.format(total));
                } catch (Exception e) {
                }
            }
        };

        //Añadir el textWatcher a los EditText para que se activen al cambiar el texto
        txtQuantity.addTextChangedListener(textWatcher);
        txtPrice.addTextChangedListener(textWatcher);


        builder.setNegativeButton("CANCEL",null);
        builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (txtName.getText().toString().isEmpty() || txtPrice.getText().toString().isEmpty() || txtQuantity.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "MISSING DATA", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product (
                            txtName.getText().toString(),
                            Integer.parseInt(txtQuantity.getText().toString()),
                            Float.parseFloat(txtPrice.getText().toString())
                    );
                    productList.add(0,product);
                    adapter.notifyItemInserted(0);
                    Toast.makeText(MainActivity.this, product.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }
}