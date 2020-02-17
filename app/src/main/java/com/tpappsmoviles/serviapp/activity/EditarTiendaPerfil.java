package com.tpappsmoviles.serviapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tpappsmoviles.serviapp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import dao.TiendaRepository;
import domain.Rubro;
import domain.Servicio;
import domain.Tienda;

public class EditarTiendaPerfil extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<Servicio> listaServicios =new ArrayList<>();

    private Tienda tienda= new Tienda();
    private EditText nombre;
    private Spinner rubro;
    private EditText telefono;
    private EditText horaInicio;
    private EditText horaFin;
    private EditText direccion;
    private ImageView imagen;
    private Bitmap imagenBitmap;

    private Button btn_foto;
    private Button btn_mapa;
    private Button btn_guardar;
    private Button btn_agregarServicio;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarperfil);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbarListFavoritos));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Editar Perfil");

        //listaP = PlatoRepository.getInstance().getListaPlatos();
        //PlatoRepository.getInstance().listarPlatos(miHandler);

        nombre =(EditText) findViewById(R.id.ep_nombreTienda);
        rubro =(Spinner) findViewById(R.id.ep_rubro);
        telefono = (EditText) findViewById(R.id.ep_telefono);
        direccion =(EditText) findViewById(R.id.ep_direccion);
        imagen= (ImageView) findViewById(R.id.ep_imagen);
        horaInicio =(EditText) findViewById(R.id.ep_horainicio);
        horaFin =(EditText) findViewById(R.id.ep_horafin);

        btn_foto= (Button) findViewById(R.id.ep_btn_cargarfoto);
        btn_mapa= (Button) findViewById(R.id.ep_btn_zonatrabajo);
        btn_guardar= (Button) findViewById(R.id.ep_btn_guardar);
        btn_agregarServicio= (Button) findViewById(R.id.ep_btn_agregarservicio);

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisosGaleria();
                selectImagen();
            }
        });
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveParametros();
            }
        });

        btn_agregarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarServicio();
            }
        });

        rubro = (Spinner) findViewById(R.id.ep_rubro);
        rubro.setAdapter(new ArrayAdapter<Rubro>(this, android.R.layout.simple_spinner_item, Rubro.values()));

        mRecyclerView = (RecyclerView) findViewById(R.id.ep_CardServicios);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServiciosRecyclerAdapter(listaServicios);
        mRecyclerView.setAdapter(mAdapter);

        Bundle extras = getIntent().getExtras();
        Integer idTienda = extras.getInt("ID_TIENDA");
        Log.d("Id recuperado en EditarPerfil", idTienda.toString());
        tienda = TiendaRepository.getInstance().buscarTienda(idTienda,miHandler);
            setParametros();

    }

    public void setParametros(){
        nombre.setText(tienda.getNombre());
 /*       telefono.setText(String.valueOf(tienda.getTelefono()));
        direccion.setText(tienda.getDireccion());
        imagen.setImageBitmap(tienda.getImagen());
*/
        //poner que muestre el rubro que ya habia seleccionado
        //este falta tmb
        //horario.setText(tienda.getHorarioDeAtencion());

      //  listaServicios=tienda.getServicios();


    }

    public void saveParametros(){
        tienda.setNombre(nombre.getText().toString());
        tienda.setRubro((Rubro) rubro.getSelectedItem());
        tienda.setTelefono(Integer.parseInt(telefono.getText().toString()));
        tienda.setHorarioDeAtencion(horaInicio.getText().toString()+" a "+horaFin.getText().toString());
        tienda.setDireccion(direccion.getText().toString());
        tienda.setImagen(imagenBitmap);
        tienda.setServicios(listaServicios);

        //guardar los cambios en tienda y despues actalizarlo en el servidor


        showToast("Datos guardados");
    }

    public void selectImagen(){
        /*Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);*/
        Intent intent = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    public void permisosGaleria(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        123);
            }
        }
    }


    public void showToast(String txtToast){
        Toast toast1 = Toast.makeText(getApplicationContext(),txtToast, Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imagenBitmap=BitmapFactory.decodeFile(picturePath);
            imagen.setImageBitmap(imagenBitmap);

        }


    }

    private void agregarServicio(){
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo servicio");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_servicio, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                EditText nombreServicio = customLayout.findViewById(R.id.as_nombre);
                EditText descripcionServicio = customLayout.findViewById(R.id.as_descripcion);
                EditText precioServicio = customLayout.findViewById(R.id.as_precio);

                try{
                Servicio servicioNuevo= new Servicio();
                servicioNuevo.setNombre(nombreServicio.getText().toString());
                servicioNuevo.setDescripcion(descripcionServicio.getText().toString());
                servicioNuevo.setPrecio(Float.valueOf(precioServicio.getText().toString()));

                listaServicios.add(servicioNuevo);
                mAdapter.notifyDataSetChanged();

                showToast(nombreServicio.getText().toString());
                } catch (NumberFormatException e) {
                    showToast("Campos nulos");
                }

            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message m){
            switch (m.arg1){
                case TiendaRepository._CONSULTA_TIENDA:

                    break;
                default:
                    break;
            }
        }
    };


}
