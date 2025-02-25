package com.example.miaplicacion

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class TresRayaActivity : AppCompatActivity(), OnClickListener{

    override fun onClick(v: View?) {
        btnSeleccionado(v as Button)
    }
     var celdas= mutableMapOf<Int, String>()
     var isX=true
     var ganador:String=""
     val totalCeldas=9
     lateinit var txtResult:TextView
     val x="X"
     val o="O"
     var btns= arrayOfNulls<Button>(totalCeldas)
     val conbinaciones: Array<IntArray> = arrayOf(
        intArrayOf(0,1,2),
        intArrayOf(3,4,5),
        intArrayOf(6,7,8),
        intArrayOf(0,3,6),
        intArrayOf(1,4,7),
        intArrayOf(2,5,8),
        intArrayOf(0,4,8),
        intArrayOf(2,4,6)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_tres_raya)
        txtResult=findViewById((R.id.txtResult))
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        for(i in 1..totalCeldas){
            var button=findViewById<Button>(resources.getIdentifier("btn$i","id",packageName)
            )
            button.setOnClickListener(this)
            btns[i-1]=button
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_inicio -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_calculadora -> {
                val intent = Intent(this, CalculatorActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_tresraya -> {
                val intent = Intent(this, TresRayaActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun btnSeleccionado(button: Button){
        var index=0
        when(button.id){
            R.id.btn1->index=0
            R.id.btn2->index=1
            R.id.btn3->index=2
            R.id.btn4->index=3
            R.id.btn5->index=4
            R.id.btn6->index=5
            R.id.btn7->index=6
            R.id.btn8->index=7
            R.id.btn9->index=8
        }
        jugar(index, button)
        comprobar()
        actualizar()
    }
    private fun comprobar(){
        if(celdas.isNotEmpty()){
            for (conbinacion in conbinaciones){
                var(a,b,c)=conbinacion
                if (celdas[a]!=null && celdas[a]==celdas[b]&& celdas[a]==celdas[c]){
                    this.ganador=celdas[a].toString()
                }
            }
        }
    }
    private fun actualizar(){
        when{
            ganador.isNotEmpty()->{
                txtResult.text=resources.getString(R.string.ganador, ganador)
                txtResult.setTextColor(Color.WHITE)
            }
            celdas.size==totalCeldas->{
                txtResult.text="Empate"
            }
            else->{
                txtResult.text=resources.getString(R.string.siguiente_jugador, if(isX)x else o)
            }
        }
    }
    private fun jugar(index:Int,button: Button){
        if (!ganador.isNullOrEmpty()){
            Toast.makeText(this,"Juego Terminado", Toast.LENGTH_LONG).show()
            return
        }
        when{
            isX->celdas[index]=x
            else->celdas[index]=o
        }
        button.text=celdas[index]
        button.isEnabled=false
        isX=!isX
    }
    fun reiniciarBotones(){
        for(i in 1..totalCeldas){
            var button=btns[i-1]
            button?.text=""
            button?.isEnabled=true
            btns[i-1]=button
        }
    }
    fun nuevoJuego(){
        celdas= mutableMapOf()
        isX=true
        ganador=""
        txtResult.text=resources.getString(R.string.siguiente_jugador,x)
        reiniciarBotones()
    }

    fun reset(view: View?){
        nuevoJuego()
    }

    fun irAMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}