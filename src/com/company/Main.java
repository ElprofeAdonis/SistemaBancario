package com.company;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

class MyDBConnection{

    private int noCuenta;
    private String titular;
    private double balance;
    private boolean activa;
    private String moneda;
    private ArrayList<String> transacciones = new ArrayList<String>();

    public MyDBConnection(int noCuenta, String titular, double balance, boolean activa, String moneda) {
        this.noCuenta = noCuenta;
        this.titular = titular;
        this.balance = balance;
        this.activa = activa;
        this.moneda = moneda;
    }

    public MyDBConnection() {

    }

    // Getters y Setters
    public int getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(int noCuenta) {
        this.noCuenta = noCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public ArrayList<String> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ArrayList<String> transacciones) {
        this.transacciones = transacciones;
    }

    public void verInfoCuenta(){
        System.out.println("Nombre del titular " + titular);
        System.out.println(" Numero de cuanta " + noCuenta);
        System.out.println("Balance de la cuenta " + balance);
        System.out.println("Tipo de moneda " + moneda);
        boolean activa = true;
        String EstadoCuenta = "";
        if (activa){
            EstadoCuenta = "Activa";
        }else{
            EstadoCuenta = "No Activa";
        }
        System.out.println("El estado de la cuenta es: " + EstadoCuenta);

    }
    public void depositarDinero(int monto) {
        this.balance = this.balance + monto;
        this.transacciones.add("Deposito - " + monto );
    }

    public void retirarDinero(int monto) {
        this.balance = this.balance - monto;
        this.transacciones.add("Retiro - " + monto);
    }

    public void enviarTransferencia(int noCuentaDestino, int monto, String detalle) {
        if (monto <= this.balance) {
            this.balance = this.balance - monto;
            this.transacciones.add("Transferencia Envio - " + noCuentaDestino + " - " + monto + " - " + detalle);
        }
    }

    public void recibirTransferencia(int noCuentaRemitente, int monto, String detalle) {
        this.balance = this.balance + monto;
        this.transacciones.add("Transferencia Recibir - " + noCuentaRemitente + " - " + monto + " - " + detalle);
    }

    public void verHistorialTransacciones() {
        if (this.transacciones.size() > 0) {
            for (int i = 0; i < this.transacciones.size(); i++) {
                System.out.println(this.transacciones.get(i));
            }
        }
        else {
            System.out.println("No hay transacciones registradas");
        }
    }

    Connection con = null;
    Statement statement = null;
    ResultSet queryResultSet = null;
    Integer queryUpdateResponse = null;
    private final String DB_URL = "jdbc:mysql://localhost:3306/SistemaBancario_nombreAdonis";
    private final String USER = "root";
    private final String PASS = "root";
    void disconnect(){
        try{
            queryResultSet.close();
            statement.close();
            con.close();
        } catch(SQLException err){
            err.printStackTrace();
        }
    }
    void connect(){
        try{
            System.out.println("Conectando a DB...");
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creando statement...");
            statement = con.createStatement();
        } catch(SQLException err){
            err.printStackTrace();
        }
    }
    ResultSet getDBData(String sqlString){
        try{
            queryResultSet = statement.executeQuery(sqlString);
        } catch (SQLException err){
            err.printStackTrace();
        }
        return queryResultSet;
    }
    Integer updateDBData(String sqlString){
        try{
            queryUpdateResponse = statement.executeUpdate(sqlString);
        } catch (SQLException err){
            err.printStackTrace();
        }
        return queryUpdateResponse;
    }
}
class Transacciones {
    private int id;
    private double monto;
    private String cuenta;
    private String detalle;
    private String tipoTransaccion;

    public Transacciones(int id, double monto, String cuenta, String detalle, String tipoTransaccion) {
        this.id = id;
        this.monto = monto;
        this.cuenta = cuenta;
        this.detalle = detalle;
        this.tipoTransaccion = tipoTransaccion;
    }

    public String ObtenerInfoTraccion(int id, double monto, String cuenta, String detalle, String tipoTransaccion){
        return "Id" + id + "monto" + monto + "cuenta" + cuenta + "detalle" + detalle + "tipoTranccion" + tipoTransaccion;
    }
}

class CuentaAhorros extends MyDBConnection {
    private double intereses;

    public CuentaAhorros(int noCuenta, String titular, double balance, boolean activa, String moneda, double intereses) {
        super(noCuenta, titular, balance, activa, moneda);
        this.intereses = intereses;
    }

    // Getters y Setters
    public double getIntereses() {
        return intereses;
    }

    public void setIntereses(double intereses) {
        this.intereses = intereses;
    }

    // Metodos
    @Override
    public void retirarDinero(int monto) {
        System.out.println("No se pueden realizar retiros de una cuenta de ahorros");
    }

    public void actualizarIntereses(double nuevoIntereses) {
        this.intereses = nuevoIntereses;
    }
}
class CuentaCorriente extends MyDBConnection {
    private double balanceMaximo;
    private boolean tieneSeguro;


    public CuentaCorriente(int noCuenta, String titular, double balance, boolean activa, String moneda, double balanceMaximo, boolean tieneSeguro) {
        super(noCuenta, titular, balance, activa, moneda);
        this.balanceMaximo = balanceMaximo;
        this.tieneSeguro = tieneSeguro;
    }

    // Getters y Setters
    public double getBalanceMaximo() {
        return balanceMaximo;
    }

    public void setBalanceMaximo(int balanceMaximo) {
        this.balanceMaximo = balanceMaximo;
    }

    public boolean isTieneSeguro() {
        return tieneSeguro;
    }

    public void setTieneSeguro(boolean tieneSeguro) {
        this.tieneSeguro = tieneSeguro;
    }

    // Metodos
    @Override
    public void depositarDinero(int monto) {
        double montoTotal = this.getBalance() + monto;
        if (montoTotal > this.balanceMaximo) {
            this.setBalance(montoTotal);
            this.getTransacciones().add("Deposito - " + monto);
        } else {
            System.out.println("No se puede exceder el balance maximo.");
        }
    }

    @Override
    public void recibirTransferencia(int noCuentaRemitente, int monto, String detalle) {
        double montoTotal = this.getBalance() + monto;
        if (montoTotal > this.balanceMaximo) {
            this.setBalance(montoTotal);
            this.getTransacciones().add("Transferencia Recibir - " + monto + " - " + detalle);
        } else {
            System.out.println("No se puede exceder el balance maximo.");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.printf("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        ArrayList cuenta1 = new ArrayList();

        CuentaCorriente miCuentaCorriente1 = new CuentaCorriente(100-000-000-9,"Daniel perez", 3000, true, "dolares", 4000,true);
        CuentaCorriente miCuentaCorriente2 = new CuentaCorriente(100-000-000-10," Rosmery Membrenio", 2300, true, "dolares", 5000,true);
        CuentaCorriente miCuentaCorriente3 = new CuentaCorriente(100-000-000-11," Valeria Valentina", 1300, true, "dolares", 3000,true);
        CuentaAhorros miCuentaAhorros1 = new CuentaAhorros(100-000-000-4," Alfonso Ali", 2000, true, "dolares", 1.45);
        CuentaAhorros miCuentaAhorros2 = new CuentaAhorros(100-000-000-5," Meche Kane", 3000, true, "dolares", 2.78);
        CuentaAhorros miCuentaAhorros3 = new CuentaAhorros(100-000-000-6," Mercedes Son", 4000, true, "dolares", 4.56);

        cuenta1.add(miCuentaCorriente1);
        cuenta1.add(miCuentaCorriente2);
        cuenta1.add(miCuentaCorriente3);
        cuenta1.add(miCuentaAhorros1);
        cuenta1.add(miCuentaAhorros2);
        cuenta1.add(miCuentaAhorros3);


        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {
            System.out.printf("\n");
            System.out.println("0. Depositar dinero");
            System.out.println("1. Retirar dinero");
            System.out.println("2. Tranferir dinero");
            System.out.println("3. Mostrar informacion de la la base de datos 1");
            System.out.println("4. Mostrar informacion guardada 2");
            System.out.println("5. Agregar informacion a la base de datos 3");
            System.out.println("6. Salir" );


            try {

                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();

                switch (opcion) {

                    case 0:

                        System.out.println("Cuantos deseas depositar");

                        Scanner abc = new Scanner(System.in);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 1");
                        int monto1 = abc.nextInt();
                        miCuentaCorriente1.depositarDinero(monto1);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 2");
                        int monto2 = abc.nextInt();
                        miCuentaCorriente2.depositarDinero(monto2);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 3");
                        int monto3 = abc.nextInt();
                        miCuentaCorriente3.depositarDinero(monto3);
                        break;


                    case 1:

                        System.out.println("Cuanto desea retiral: ");
                        Scanner abc1 = new Scanner(System.in);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 1");
                        int monto4 = abc1.nextInt();
                        miCuentaCorriente1.retirarDinero(monto4);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 2");
                        int monto5 = abc1.nextInt();
                        miCuentaCorriente2.retirarDinero(monto5);

                        System.out.println("Cual es el monto que desea depositar en la cuenta 3");
                        int monto6 = abc1.nextInt();
                        miCuentaCorriente3.retirarDinero(monto6);
                        break;


                    case 2:
                        System.out.println("Has seleccionado la opcion 2");
                        Scanner dabc = new Scanner(System.in);

                        System.out.println("Digite el numero de cuenta destito: ");
                        int noCuentaRemitente = dabc.nextInt();

                        System.out.println("Digite el monto a enviar: ");
                        int montooo1 = dabc.nextInt();

                        System.out.println("Digete numero el detalle de la trnaferencia: ");
                        String detalle11 = dabc.nextLine();

                        miCuentaAhorros1.enviarTransferencia(100 - 000 - 000 - 4, 2000, "Pago del mercado");
                        miCuentaCorriente1.verHistorialTransacciones();

                        System.out.println("++++++++++++++++++++++++++++++++++++++++");
                        miCuentaCorriente1.recibirTransferencia(noCuentaRemitente, montooo1, detalle11);
                        miCuentaCorriente1.verHistorialTransacciones();
                        miCuentaCorriente1.verInfoCuenta();


                        break;
                    case 3:
                        System.out.println("Informcacion  1");
                        MyDBConnection adoDeDB = new MyDBConnection();

                        try {
                            adoDeDB.connect();
                            ResultSet transaccionDeDB = adoDeDB.getDBData("SELECT id, monto, cuenta, detalle, tipoTransaccion, cuentaDeTransaccion FROM transaccion");

                            System.out.println("******************************");
                            System.out.println("|                              Mostrar informacion de la la base de datos                               |");
                            System.out.println("******************************");
                            System.out.println("|  ID   *          Monto       *          Cuenta        *         Detalle         *                 tipoTransaccion                      *                 cuentaDeTransaccion              |");

                            while (transaccionDeDB.next()) {
                                int id = transaccionDeDB.getInt("id");
                                String monto = transaccionDeDB.getString("monto");
                                String cuenta = transaccionDeDB.getString("cuenta");
                                String detalle = transaccionDeDB.getString("detalle");
                                String tipoTransaccion = transaccionDeDB.getString("tipoTransaccion");
                                String cuentaDeTransaccion = transaccionDeDB.getString("cuentaDeTransaccion");
                                System.out.println(" | " + "ID: " + id + " | " + " • Monto: " + monto + " | " + " • Cuenta: " + cuenta + " | " + " • detalle: " + detalle + " | " + "  tipoTransaccion: " + tipoTransaccion + " | " + "  cuentaDeTransaccion: " + cuentaDeTransaccion);
                                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            }
//                            ResultSet NombresComida = adoDeDB.getDBData("SELECT nombre FROM Comidas");
//                            while(NombresComida.next()){
//                                String nombre = NombresComida.getString("nombre");
//                                System.out.println("----------------------");
//                                System.out.println("• Nombre: " + nombre);
//                            }
                            System.out.println("Se acabo el programa!");
                            adoDeDB.disconnect();
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                        break;
                    case 4:
                        System.out.println(" informacion guardadas 2");
//                        ArrayList cuentas = new ArrayList();
//
//                        CuentaCorriente miCuentaCorriente1 = new CuentaCorriente(100-000-000-4,"Fidel perez", 3000, true, "dolares", 4000,true);
//                        CuentaCorriente miCuentaCorriente2 = new CuentaCorriente(100-000-000-5," Pamela Membrenio", 2300, true, "dolares", 5000,true);
//                        CuentaCorriente miCuentaCorriente3 = new CuentaCorriente(100-000-000-8," Danna Valentina", 1300, true, "dolares", 3000,true);
//                        CuentaAhorros miCuentaAhorros1 = new CuentaAhorros(100-000-000-1," Dele Ali", 2000, true, "dolares", 1.45);
//                        CuentaAhorros miCuentaAhorros2 = new CuentaAhorros(100-000-000-2," Harry Kane", 3000, true, "dolares", 2.78);
//                        CuentaAhorros miCuentaAhorros3 = new CuentaAhorros(100-000-000-3," Min Son", 4000, true, "dolares", 4.56);

                        try {

                            miCuentaCorriente1.depositarDinero(25);
                            miCuentaCorriente2.depositarDinero(25);
                            miCuentaCorriente3.depositarDinero(25);
                            miCuentaCorriente1.retirarDinero(50);
                            miCuentaCorriente2.retirarDinero(50);
                            miCuentaCorriente3.retirarDinero(50);
                            System.out.println("-+-++--+-+-+--+--+-+-+-+-+--");

                            miCuentaAhorros1.depositarDinero(5000);
                            miCuentaAhorros2.depositarDinero(5000);
                            miCuentaAhorros3.retirarDinero(5000);
                            System.out.println("===============================================");

                            miCuentaCorriente1.enviarTransferencia(2, 20, " Tranferencia de micuentaCorriente 1 a micuentaCorriente 2 " + " de pago - servicios profesionales");
                            miCuentaCorriente2.enviarTransferencia(3, 20, " Tranferencia de micuentaCorriente 2 a micuentaCorriente 3 " + " de pago - salida a comer");
                            miCuentaCorriente3.enviarTransferencia(1, 20, "  Tranferencia de micuentaCorriente 3 a micuentaAhorro 1 " + " pago de mudanza");
                            miCuentaAhorros1.enviarTransferencia(2, 30, "   Tranferencia de micuentaAhorro 1 a micuentaAhorro 2 " + " pago corte de cabello");
                            miCuentaAhorros2.enviarTransferencia(3, 30, "   Tranferencia de micuentaAhorro 2 a micuentaAhorro 3 " + " pago apuesta");
                            miCuentaAhorros3.enviarTransferencia(1, 30, "  Tranferencia de micuentaAhorro 3 a micuentaCorriente 1 " + " pago - compra de cable");


                            System.out.println("++++++++++++++++++++++++++++++++++++++++");
                            miCuentaCorriente1.verHistorialTransacciones();
                            miCuentaCorriente1.verInfoCuenta();
                            System.out.println("----------------------------------------");
                            miCuentaCorriente2.verHistorialTransacciones();
                            miCuentaCorriente2.verInfoCuenta();
                            System.out.println("---------------------------------------");
                            miCuentaCorriente3.verHistorialTransacciones();
                            miCuentaCorriente3.verInfoCuenta();
                            System.out.println("--------------------------------------");
                            miCuentaAhorros1.verHistorialTransacciones();
                            miCuentaAhorros1.verInfoCuenta();
                            System.out.println("--------------------------------------");
                            miCuentaAhorros2.verHistorialTransacciones();
                            miCuentaAhorros2.verInfoCuenta();
                            System.out.println("-------------------------------------");
                            miCuentaAhorros3.verHistorialTransacciones();
                            miCuentaAhorros3.verInfoCuenta();
                            System.out.println("------------------------------------");

                            System.out.println("Se acabo el programa!");

                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                        break;
                    case 5:
                        class informacion extends MyDBConnection  {
                            static final String DB_URL = "jdbc:mysql://localhost:3306/SistemaBancario_nombreAdonis";
                            static final String USER = "root";
                            static final String PASS = "root";

                            public static void main(String[] args) {
                                Connection con = null;
                                Statement statement = null;
                                try {
                                    System.out.println("Conectando a DB...");
                                    con = DriverManager.getConnection(DB_URL, USER, PASS);
                                    System.out.println("Creando statement...");
                                    statement = con.createStatement();
                                    String sql;
//                                        Crear registro en DB
                                    sql = "INSERT INTO transaccion VALUES (NULL, '2000', '100-000-000-9', 'pago de comida' , 'Por medio de una tranferencia simpe', '100-000-000-1' )";
                                    Integer respuesta = statement.executeUpdate(sql);
                                    System.out.println(respuesta);
                                    System.out.println("Registro agregado con exito!");
                                    sql = "SELECT id, monto, cuenta, detalle, tipoTransaccion, cuentaDeTransaccion FROM transaccion";
                                    ResultSet transaccionDeDb = statement.executeQuery(sql);
                                    while (transaccionDeDb.next()) {
                                        int id = transaccionDeDb.getInt("id");
                                        double monto = transaccionDeDb.getDouble("monto");
                                        int cuenta = transaccionDeDb.getInt("cuenta");
                                        String detalle = transaccionDeDb.getString("detalle");
                                        String tipoTransaccion = transaccionDeDb.getString("tipoTransaccion");
                                        String cuentaDeTransaccion = transaccionDeDb.getString("cuentaDeTransaccion");
                                        System.out.println("ID: " + id + " • Monto: " + monto + " • Cuenta: " + cuenta + " • Tetalle: " + detalle + " tipoTransaccion " + tipoTransaccion + " cuentaDeTransaccion" + cuentaDeTransaccion);
                                    }
//          Cleanup
                                    transaccionDeDb.close();
                                    statement.close();
                                    con.close();

                                } catch (SQLException er) {
                                    er.printStackTrace();
                                }

                            }
                        }
                        break;
                    case 6:

                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");

                }
            }catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();

            }
            System.out.printf("-----------------------------------------------------------------------------------------");

        }

    }
}



