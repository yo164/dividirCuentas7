package com.alanturin.dividircuentas7

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alanturin.dividircuentas7.ui.theme.DividirCuentas7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DividirCuentas7Theme {
                var cuenta by remember {mutableStateOf("")}
                var total by remember {mutableStateOf("")}
                var comensales by remember {mutableStateOf("")}
                var checked by remember {mutableStateOf(false)}
                var sliderPosition by remember { mutableFloatStateOf(0f) }
                var resultado by remember { mutableStateOf("")}
                var mostrarResultado by remember {mutableStateOf(false)}
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)

                    ) {
                        TextField(
                            value = cuenta,
                            onValueChange = {cuenta = it},
                            label = {Text (stringResource(R.string.quantity))},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        TextField(
                            value = comensales,
                            onValueChange = {comensales = it},
                            label = {Text(stringResource(R.string.people))},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                stringResource(R.string.Round_tip)

                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Switch(

                                checked = checked,
                                onCheckedChange = {
                                    checked = it
                                    sliderPosition = if (it) 1f else 0f
                                }

                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        if (checked){
                            Slider(
                                value = sliderPosition,
                                onValueChange = { sliderPosition = it },
                                valueRange = 0f..5f,
                                steps = 4
                            )
                            Text(
                                text = sliderPosition.toInt().toString()
                            )
                        } else{
                            Slider(
                                value = sliderPosition,
                                onValueChange = { sliderPosition = it},
                                valueRange = 0f..5f,
                                steps = 4,
                                enabled = false
                            )
                        }


                        Spacer(modifier = Modifier.height(24.dp))


                        var activaBoton = !(cuenta.isEmpty() || comensales.isEmpty())
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = activaBoton,
                            onClick = {
                            total = hacerCuenta(
                                cantidad = cuenta,
                                sliderPosition = sliderPosition,

                                )
                            resultado = dividirCuenta(
                                total = total,
                                comensales = comensales
                            )
                            mostrarResultado = true

                        }) {
                            Text(stringResource(R.string.calculate))
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        if (mostrarResultado){
                            Text( stringResource(R.string.total_quantity) + " $total")
                            Text(stringResource(R.string.each_quantity) + " $resultado")
                        }

                    }

                }
            }
        }
    }
}



fun dividirCuenta(total: String, comensales: String): String {
    val comensalesF = comensales.toFloat()
    val totalF = total.toFloat()
    return (totalF/comensalesF).toString()
}

fun hacerCuenta(cantidad: String, sliderPosition: Float) :String{
    var cuenta = cantidad.toFloat()
    val porcentaje = sliderPosition * 0.05f
    val cantidadDePropina = cuenta * porcentaje
    val total: Float = cuenta + cantidadDePropina

    return total.toString()

}