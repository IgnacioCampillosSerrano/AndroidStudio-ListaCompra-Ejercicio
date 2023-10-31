package ignacio.campillos.androidstudio_listacompra_ejercicio.adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                objects.remove(product);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
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
