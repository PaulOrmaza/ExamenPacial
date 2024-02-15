package com.spoj.examenparcial;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class FormularioVehiculo extends AppCompatActivity {

    private final static String CHANNEL_ID="canal";
    private EditText txtCedula, txtNombre, txtPlaca, txtFbVehiculo, txtMarca, txtColor, txtTipo, txtValor, txtMultas;
    private Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_vehiculo);

        txtCedula = findViewById(R.id.txtcedula);
        txtNombre = findViewById(R.id.txtnombre);
        txtPlaca = findViewById(R.id.txtplaca);
        txtFbVehiculo = findViewById(R.id.txtfbvehiculo);
        txtMarca = findViewById(R.id.txtmarca);
        txtColor = findViewById(R.id.txtcolor);
        txtTipo = findViewById(R.id.txttipo);
        txtValor = findViewById(R.id.txtvalor);
        txtMultas = findViewById(R.id.txtmultas);
        btnPagar = findViewById(R.id.btnconsultar);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            generarNoticacionCanal();
        }else{
            generarNoticacionSinCanal();
        }

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = txtCedula.getText().toString();
                String nombre = txtNombre.getText().toString();
                String placa = txtPlaca.getText().toString();
                String fbVehiculo = txtFbVehiculo.getText().toString();
                String marca = txtMarca.getText().toString();
                String color = txtColor.getText().toString();
                String tipo = txtTipo.getText().toString();
                String valor = txtValor.getText().toString();
                String multas = txtMultas.getText().toString();

                // Crear instancia de la clase BDHelper
                BDHelper admin = new BDHelper(FormularioVehiculo.this, "fichaVehiculo.db", null, 1);

                // Obtener instancia de la base de datos en modo escritura
                SQLiteDatabase bd = admin.getWritableDatabase();

                if (!placa.isEmpty() && !color.isEmpty() && !marca.isEmpty() && !tipo.isEmpty() && !valor.isEmpty()) {
                    ContentValues datosVehiculo = new ContentValues();
                    datosVehiculo.put("veh_cedula", cedula);
                    datosVehiculo.put("veh_nombre", nombre);
                    datosVehiculo.put("veh_placa", placa);
                    datosVehiculo.put("veh_fb", fbVehiculo);
                    datosVehiculo.put("veh_marca", marca);
                    datosVehiculo.put("veh_color", color);
                    datosVehiculo.put("veh_tipo", tipo);
                    datosVehiculo.put("veh_valor", Double.parseDouble(valor));
                    datosVehiculo.put("veh_multas", Integer.parseInt(multas));

                    // Insertar los datos del vehículo en la tabla tblVehiculos
                    long resultado = bd.insert("tblVehiculos", null, datosVehiculo);

                    if (resultado != -1) {
                        Toast.makeText(FormularioVehiculo.this, "VEHICULO REGISTRADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(FormularioVehiculo.this, "Error al registrar el vehículo", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(FormularioVehiculo.this, "Por favor complete todos los campos", Toast.LENGTH_LONG).show();
                }

                // Cerrar la conexión de la base de datos al finalizar
                bd.close();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generarNoticacionCanal(){
        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        generarNoticacionSinCanal();
    }
    public void generarNoticacionSinCanal(){

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_add_alert_24)
                .setContentTitle("Operaciones Matemáticas GGVC")
                .setContentText("Aplicación que permite realizar operaciones básicas")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Cálculos Matemáticos"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Obtener el NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Mostrar la notificación
        notificationManager.notify(0, builder.build());
    }
}
