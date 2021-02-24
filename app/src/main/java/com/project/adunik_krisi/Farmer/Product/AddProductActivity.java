package com.project.adunik_krisi.Farmer.Product;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.adunik_krisi.Constant;
import com.project.adunik_krisi.Farmer.Product.model.ProductUpload;
import com.project.adunik_krisi.R;
import com.project.adunik_krisi.remote.ApiClient;
import com.project.adunik_krisi.remote.ApiInterface;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    EditText etxtProductName, etxtQuantity, etxtCategory, etxtPrice, etxtDescription;
    Button txtChooseImage, txtSubmit;
    ImageView imgProduct;
    String mediaPath, product_name, product_quantity, product_category, product_description, product_price, spCell;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setTitle("নতুন পণ্য যোগ");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        etxtProductName = findViewById(R.id.etxt_product_name);
        etxtCategory = findViewById(R.id.etxt_categoryProduct);
        etxtDescription = findViewById(R.id.etxt_descriptionProduct);
        etxtPrice = findViewById(R.id.etxt_priceProduct);
        imgProduct = findViewById(R.id.image_Addproduct);
        etxtQuantity = findViewById(R.id.etxt_quantityProduct);


        txtChooseImage = findViewById(R.id.btn_imageAddProduct);
        txtSubmit = findViewById(R.id.txt_submitAddProduct);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        //Fetching cell from shared preferences
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        spCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");


        //For choosing account type and open alert dialog
        etxtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] categoryList = {"বোরো", "আউশ", "আমন", "রোপা আমন", "বোনা আমন", "বোনা এবং রোপা আউশ"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                builder.setTitle("ধানের জাত নির্ধারণ করুন");
                //builder.setIcon(R.drawable.ic_gender);


                builder.setCancelable(false);
                builder.setItems(categoryList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtCategory.setText(categoryList[position]);
                                break;

                            case 1:
                                etxtCategory.setText(categoryList[position]);
                                break;

                            case 2:
                                etxtCategory.setText(categoryList[position]);
                                break;
                            case 3:
                                etxtCategory.setText(categoryList[position]);
                                break;
                            case 4:
                                etxtCategory.setText(categoryList[position]);
                                break;
                            case 5:
                                etxtCategory.setText(categoryList[position]);
                                break;


                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog categoryDialog = builder.create();

                categoryDialog.show();
            }

        });

        txtChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddProductActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);
            }
        });


        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                product_name = etxtProductName.getText().toString().trim();
                product_category = etxtCategory.getText().toString().trim();
                product_description = etxtDescription.getText().toString().trim();
                product_price = etxtPrice.getText().toString().trim();
                product_quantity = etxtQuantity.getText().toString();

                if (product_name.isEmpty()) {
                    etxtProductName.setError("পণ্যের নাম খালি থাকতে পারবে না!");
                    etxtProductName.requestFocus();
                } else if (product_category.isEmpty()) {
                    etxtCategory.setError("পণ্যের ধরন খালি থাকতে পারবে না!");
                    etxtCategory.requestFocus();
                } else if (product_quantity.isEmpty()) {
                    etxtQuantity.setError("পণ্যের পরিমাণ খালি থাকতে পারবে না!");
                    etxtQuantity.requestFocus();
                } else if (product_price.isEmpty()) {
                    etxtPrice.setError("পণ্যের দাম খালি থাকতে পারবে না!");
                    etxtPrice.requestFocus();
                } else if (product_description.isEmpty()) {
                    etxtDescription.setError("পণ্যের বিবরণ খালি থাকতে পারবে না!");
                    etxtDescription.requestFocus();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                    builder.setMessage("পণ্য যোগ করতে নিশ্চিত?")
                            .setCancelable(false)
                            .setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    // Perform Your Task Here--When Yes Is Pressed.
                                    //call method
                                    uploadFile(product_name, product_category, product_price, product_description);
                                    dialog.cancel();

                                }
                            })
                            .setNegativeButton("না", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Perform Your Task Here--When No is pressed
                                    dialog.cancel();
                                }
                            }).show();


                }


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {


                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                imgProduct.setImageBitmap(selectedImage);


            }


        } catch (Exception e) {
            Toast.makeText(this, "ছবি হালনাগাদে কোন একটা সমস্যা হচ্ছে...", Toast.LENGTH_LONG).show();
        }

    }


    // Uploading Image/Video
    private void uploadFile(String name, String category, String price, String description) {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody p_name = RequestBody.create(MediaType.parse("text/plain"), product_name);
        RequestBody p_category = RequestBody.create(MediaType.parse("text/plain"), product_category);
        RequestBody p_quantity = RequestBody.create(MediaType.parse("text/plain"), product_quantity);
        RequestBody p_price = RequestBody.create(MediaType.parse("text/plain"), product_price);
        RequestBody p_description = RequestBody.create(MediaType.parse("text/plain"), product_description);
        RequestBody sp_cell = RequestBody.create(MediaType.parse("text/plain"), spCell);


        ApiInterface getResponse = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductUpload> call = getResponse.uploadFile(fileToUpload, filename, p_name, p_category, p_quantity, p_price, p_description, sp_cell);
        call.enqueue(new Callback<ProductUpload>() {
            @Override
            public void onResponse(Call<ProductUpload> call, Response<ProductUpload> response) {
                ProductUpload serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toasty.success(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this, AllProductActivity.class));

                    } else {
                        Toasty.error(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductUpload> call, Throwable t) {

            }
        });
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
