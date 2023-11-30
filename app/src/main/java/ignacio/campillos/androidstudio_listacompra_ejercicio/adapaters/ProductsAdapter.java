package ignacio.campillos.androidstudio_listacompra_ejercicio.adapaters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.NonUiContext;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ignacio.campillos.androidstudio_listacompra_ejercicio.R;
import ignacio.campillos.androidstudio_listacompra_ejercicio.modelos.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductVH> {

    private List<Product> objects;
    private int resource;
    private Context context;

    public ProductsAdapter(List<Product> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(context).inflate(resource,null);
        productView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ProductVH(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {
        Product product = objects.get(position);

        holder.lbName.setText(product.getName());
        holder.lbQuantity.setText(String.valueOf(product.getQuantity()));


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(product).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate(product).show();
            }
        });
    }

    private AlertDialog confirmUpdate(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.update);
        builder.setCancelable(false);

        //CARGAR LA VISTA EN EL ALERT DIALOG

        View productViewModel = LayoutInflater.from(context).inflate(R.layout.product_view_model,null);
        EditText txtName = productViewModel.findViewById(R.id.txtNameProductViewModel);
        txtName.setEnabled(false);
        EditText txtQuantity = productViewModel.findViewById(R.id.txtQuantityProductViewModel);
        EditText txtPrice = productViewModel.findViewById(R.id.txtPriceProductViewModel);
        TextView lbTotal = productViewModel.findViewById(R.id.lbTotalProductViewModel);
        builder.setView(productViewModel);

        txtName.setText(product.getName());
        txtQuantity.setText(String.valueOf(product.getQuantity()));
        txtPrice.setText(String.valueOf(product.getPrice()));
        lbTotal.setText((String.valueOf(product.getTotal())));

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
                    lbTotal.setText(String.valueOf(total));
                }catch (Exception e){

                }
            }
        };
        txtQuantity.addTextChangedListener(textWatcher);
        txtPrice.addTextChangedListener(textWatcher);

        builder.setNegativeButton(R.string.buttonCancel, null);
        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    if (txtQuantity.getText().toString().isEmpty() || txtPrice.getText().toString().isEmpty()){
                        Toast.makeText(context, R.string.missingData, Toast.LENGTH_SHORT).show();
                    } else {
                        product.setQuantity(Integer.parseInt(txtQuantity.getText().toString()));
                        product.setPrice(Float.parseFloat(txtPrice.getText().toString()));
                        notifyItemChanged(objects.indexOf(product));
                    }
            }
        });
        return builder.create();
    }
    private AlertDialog confirmDelete(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmDelete);
        builder.setCancelable(false);

        builder.setNegativeButton(R.string.buttonCancel,null);
        builder.setPositiveButton(R.string.buttonOk, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = objects.indexOf(product);
                objects.remove(product);
                notifyItemRemoved(position);
            }
        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ProductVH extends RecyclerView.ViewHolder{

        TextView lbName;
        TextView lbQuantity;
        ImageButton btnDelete;
        public ProductVH(@NonNull View itemView) {
            super(itemView);

            lbName = itemView.findViewById(R.id.lbNameProductViewHolder);
            lbQuantity = itemView.findViewById(R.id.lbQuantityProductViewHolder);
            btnDelete = itemView.findViewById(R.id.btnDeleteProductViewHolder);
        }
    }
}
