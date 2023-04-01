package com.noasecond.nekoflora;

import static com.noasecond.nekoflora.MainActivity.selectedProducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    Product choosedProduct;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //Bundle
        Bundle bundle = getIntent().getExtras();
        String mainContextString = bundle.getString("mainContext");
        Context mainContext = getApplicationContext();
        if (mainContextString != null) {
            mainContext = (Context) getApplicationContext().getSystemService(ContextWrapper.class);
            mainActivity = (MainActivity) mainContext;
        }
        //Intent
        Intent intent = getIntent();
        choosedProduct = (Product) intent.getSerializableExtra("ChoosedProduct");

        //Init components
        TextView tv_titleProduct = (TextView) findViewById(R.id.tv_titleProduct);
        TextView tv_productPageDesc = (TextView) findViewById(R.id.tv_descriptionProduct);
        GridView gv_productPage = (GridView) findViewById(R.id.gv_productPage);
        ImageView iv_shoppingCartProduct = (ImageView) findViewById(R.id.iv_shoppingCartProduct);
        Button btn_addToCart = (Button) findViewById(R.id.btn_addToCart);

        //Define components
        ArrayList<Product> adapterList = new ArrayList<>();
        adapterList.add(choosedProduct);
        ProductAdapter adapter = new ProductAdapter(this, adapterList);
        GridView display = findViewById(R.id.gv_productPage);
        display.setAdapter(adapter);
        tv_titleProduct.setText("Produit :");
        tv_productPageDesc.setText(choosedProduct.getProductDescription());

        //Bind shopping cart
        iv_shoppingCartProduct.setOnClickListener(e -> {
            if(selectedProducts.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Veuillez choisir au moin 1 produit.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
                intentCart.putExtra("SelectedProducts", selectedProducts);
                startActivity(intentCart);
            }
        });

        //Bind add to cart
        btn_addToCart.setOnClickListener(e -> {
            selectedProducts.add(choosedProduct);
            Toast.makeText(getApplicationContext(), "Produit ajouté au pannier !", Toast.LENGTH_SHORT).show();
        });
    }
}