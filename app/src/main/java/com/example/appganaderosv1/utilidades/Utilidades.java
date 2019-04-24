package com.example.appganaderosv1.utilidades;

public class Utilidades {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla usuario
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_ID_USUARIO = "id_usuario";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_CONTRASENA = "contrasena";
    public static final String CAMPO_TIPO_USUARIO = "tipo_usuario";

    public static final String CREAR_TABLA_USUARIO =
            "CREATE TABLE " + TABLA_USUARIO + "("
                    + CAMPO_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_USUARIO + " TEXT, "
                    + CAMPO_CONTRASENA + " TEXT, "
                    + CAMPO_TIPO_USUARIO + " TEXT)";
    public static final String INSERTAR_USUARIOS =
            "INSERT INTO usuario " +
                    "(usuario, contrasena, tipo_usuario)" +
                    "values" +
                    "('Felipe', '123', 'Normal')," +
                    "('Admin', 'admin', 'Administrador')";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla persona
    public static final String TABLA_PERSONA = "persona";
    public static final String CAMPO_ID_PERSONA = "id_persona";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_DOMICILIO = "domicilio";
    public static final String CAMPO_DATOS_EXTRAS = "extras";

    public static final String CREAR_TABLA_PERSONA =
            "CREATE TABLE " + TABLA_PERSONA + "("
                    + CAMPO_ID_PERSONA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_NOMBRE + " TEXT, "
                    + CAMPO_TELEFONO + " TEXT, "
                    + CAMPO_DOMICILIO + " TEXT, "
                    + CAMPO_DATOS_EXTRAS + " TEXT)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Citas
    public static final String TABLA_CITAS = "citas";
    public static final String CAMPO_ID_CITAS = "id_cita";
    public static final String CAMPO_CANTIDAD_GANADO = "cantidad_ganado";
    public static final String CAMPO_DATOS = "datos";
    public static final String CAMPO_FECHA_CITAS = "fecha";
    public static final String CAMPO_PERSONA_CITA = "persona_cita";
    public static final String CAMPO_RESPALDO_CITAS = "respaldo";

    public static final String CREAR_TABLA_CITAS =
            "CREATE TABLE " + TABLA_CITAS + "("
                    + CAMPO_ID_CITAS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_CANTIDAD_GANADO + " INTEGER, "
                    + CAMPO_DATOS + " TEXT, "
                    + CAMPO_FECHA_CITAS + " TEXT, "
                    + CAMPO_RESPALDO_CITAS + " INTEGER, "
                    + CAMPO_PERSONA_CITA + " INTEGER REFERENCES " + TABLA_PERSONA + "(" + CAMPO_ID_PERSONA + "))";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Compras
    public static final String TABLA_COMPRAS = "compras";
    public static final String CAMPO_ID_COMPRA = "id_compras";
    public static final String CAMPO_PERSONA_COMPRO = "persona_compro";
    public static final String CAMPO_FECHA_COMPRAS = "fecha";
    public static final String CAMPO_CANTIDAD_ANIMALES_COMPRAS = "cantidad_animales";
    public static final String CAMPO_CANTIDAD_PAGAR = "cantidad_pagar";
    public static final String CAMPO_RESPALDO_COMPRAS = "respaldo";

    public static final String CREAR_TABLA_COMPRAS =
            "CREATE TABLE " + TABLA_COMPRAS + "("
                    + CAMPO_ID_COMPRA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_PERSONA_COMPRO + " INTEGER REFERENCES " + TABLA_PERSONA + "(" + CAMPO_ID_PERSONA + "), "
                    + CAMPO_FECHA_COMPRAS + " TEXT, "
                    + CAMPO_CANTIDAD_ANIMALES_COMPRAS + " INTEGER, "
                    + CAMPO_CANTIDAD_PAGAR + " REAL, "
                    + CAMPO_RESPALDO_COMPRAS + " INTEGER)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla raza
    public static final String TABLA_RAZA = "raza";
    public static final String CAMPO_ID_RAZA = "id_raza";
    public static final String CAMPO_TIPO_RAZA = "tipo_raza";

    public static final String CREAR_TABLA_RAZA =
            "CREATE TABLE " + TABLA_RAZA + "("
                    + CAMPO_ID_RAZA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_TIPO_RAZA + " TEXT)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Ganado
    public static final String TABLA_GANADO = "ganado";
    public static final String CAMPO_ID_GANADO = "id_ganado";
    public static final String CAMPO_TIPO_GANADO = "tipo_ganado";

    public static final String CREAR_TABLA_GANADO =
            "CREATE TABLE " + TABLA_GANADO + "("
                    + CAMPO_ID_GANADO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_TIPO_GANADO + " TEXT)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Compra Detalle
    public static final String TABLA_COMPRA_DETALLE = "compra_detalle";
    public static final String CAMPO_ID_COMPRA_DETALLE = "id_compra_detalle";
    public static final String CAMPO_GANADO = "ganado";
    public static final String CAMPO_RAZA = "raza";
    public static final String CAMPO_PESO = "peso";
    public static final String CAMPO_PRECIO = "precio_compra";
    public static final String CAMPO_TARA = "tara_compra";
    public static final String CAMPO_TOTAL_PAGAR = "total";
    public static final String CAMPO_NUMERO_ARETE = "numero_arete";
    public static final String CAMPO_COMPRA = "compra";

    public static final String CREAR_TABLA_COMPRA_DETALLE =
            "CREATE TABLE " + TABLA_COMPRA_DETALLE + "("
                    + CAMPO_ID_COMPRA_DETALLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_GANADO + " INTEGER REFERENCES " + TABLA_GANADO + "(" + CAMPO_ID_GANADO + "), "
                    + CAMPO_RAZA + " INTEGER REFERENCES " + TABLA_RAZA + "(" + CAMPO_ID_RAZA + "), "
                    + CAMPO_PESO + " REAL, "
                    + CAMPO_PRECIO + " REAL, "
                    + CAMPO_TARA + " REAL, "
                    + CAMPO_TOTAL_PAGAR + " REAL, "
                    + CAMPO_NUMERO_ARETE + " INTEGER, "
                    + CAMPO_COMPRA + " INTEGER REFERENCES " + TABLA_COMPRAS + "(" + CAMPO_ID_COMPRA + "))";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Ventas
    public static final String TABLA_VENTAS = "ventas";
    public static final String CAMPO_ID_VENTAS = "id_ventas";
    public static final String CAMPO_PERSONA_VENTA = "persona_venta";
    public static final String CAMPO_FECHA_VENTAS = "fecha";
    public static final String CAMPO_CANTIDAD_ANIMALES_VENTAS = "cantidad_animales";
    public static final String CAMPO_CANTIDAD_COBRAR = "cantidad_cobrar";
    public static final String CAMPO_GANANCIAS = "ganancias";
    public static final String CAMPO_RESPALDO_VENTAS = "respaldo";

    public static final String CREAR_TABLA_VENTAS =
            "CREATE TABLE " + TABLA_VENTAS + "("
                    + CAMPO_ID_VENTAS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_PERSONA_VENTA + " INTEGER REFERENCES " + TABLA_PERSONA + "(" + CAMPO_ID_PERSONA + "), "
                    + CAMPO_FECHA_VENTAS + " TEXT, "
                    + CAMPO_CANTIDAD_ANIMALES_VENTAS + " INTEGER, "
                    + CAMPO_CANTIDAD_COBRAR + " REAL, "
                    + CAMPO_GANANCIAS + " REAL, "
                    + CAMPO_RESPALDO_VENTAS + " INTEGER)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Venta Detalle
    public static final String TABLA_VENTA_DETALLE = "venta_detalle";
    public static final String CAMPO_ID_VENTA_DETALLE = "id_venta_detalle";
    public static final String CAMPO_COMPRA_GANADO = "compra_animal";
    public static final String CAMPO_PRECIO_VENTA = "precio_venta";
    public static final String CAMPO_TARA_VENTA = "tara_venta";
    public static final String CAMPO_TOTAL_VENTA = "total_ct";
    public static final String CAMPO_VENTA = "venta";

    public static final String CREAR_TABLA_VENTA_DETALLE =
            "CREATE TABLE " + TABLA_VENTA_DETALLE + "("
                    + CAMPO_ID_VENTA_DETALLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_COMPRA_GANADO + " INTEGER REFERENCES " + TABLA_COMPRA_DETALLE + "(" + CAMPO_ID_COMPRA_DETALLE + "), "
                    + CAMPO_PRECIO_VENTA + " REAL, "
                    + CAMPO_TARA_VENTA + " REAL, "
                    + CAMPO_TOTAL_VENTA + " REAL, "
                    + CAMPO_VENTA + " INTEGER REFERENCES " + TABLA_VENTAS + "(" + CAMPO_ID_VENTA_DETALLE + "))";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista de citas
    public static final String VIEW_CITA = "appointment_view";

    public static final String SELECT_CITA =
            "SELECT " +
                    Utilidades.CAMPO_ID_CITAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_GANADO + ", " +
                    Utilidades.CAMPO_DATOS + ", " +
                    Utilidades.CAMPO_FECHA_CITAS + ", " +

                    Utilidades.CAMPO_ID_PERSONA + ", " +
                    Utilidades.CAMPO_NOMBRE + ", " +
                    Utilidades.CAMPO_TELEFONO + ", " +
                    Utilidades.CAMPO_DOMICILIO + ", " +
                    Utilidades.CAMPO_DATOS_EXTRAS +
                    " FROM " +
                    Utilidades.TABLA_PERSONA + ", " +
                    Utilidades.TABLA_CITAS +
                    " WHERE " +
                    Utilidades.CAMPO_PERSONA_CITA + " = " + Utilidades.CAMPO_ID_PERSONA +
                    " AND " +
                    Utilidades.CAMPO_RESPALDO_CITAS + " = " + 0;

    public static final String CREAR_VISTA_CITAS = "CREATE VIEW " + VIEW_CITA + " AS " + SELECT_CITA;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista de compras
    public static final String VIEW_COMPRAS = "purchase_view";

    public static final String SELECT_COMPRA =
            "SELECT " +
                    Utilidades.CAMPO_ID_COMPRA + ", " +
                    Utilidades.CAMPO_FECHA_COMPRAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_PAGAR + ", " +

                    Utilidades.CAMPO_ID_PERSONA + ", " +
                    Utilidades.CAMPO_NOMBRE + ", " +
                    Utilidades.CAMPO_TELEFONO + ", " +
                    Utilidades.CAMPO_DOMICILIO + ", " +
                    Utilidades.CAMPO_DATOS_EXTRAS +
                    " FROM " +
                    Utilidades.TABLA_PERSONA + ", " +
                    Utilidades.TABLA_COMPRAS +
                    " WHERE " +
                    Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                    " AND " +
                    Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 0;

    public static final String CREAR_VISTA_COMPRAS = "CREATE VIEW " + VIEW_COMPRAS + " AS " + SELECT_COMPRA;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista de ventas
    public static final String VIEW_VENTAS = "sales_view";

    public static final String SELECT_VENTA =
            "SELECT " +
                    Utilidades.CAMPO_ID_VENTAS + ", " +
                    Utilidades.CAMPO_FECHA_VENTAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_COBRAR + ", " +
                    Utilidades.CAMPO_GANANCIAS + ", " +

                    Utilidades.CAMPO_ID_PERSONA + ", " +
                    Utilidades.CAMPO_NOMBRE + ", " +
                    Utilidades.CAMPO_TELEFONO + ", " +
                    Utilidades.CAMPO_DOMICILIO + ", " +
                    Utilidades.CAMPO_DATOS_EXTRAS +
                    " FROM " +
                    Utilidades.TABLA_PERSONA + ", " +
                    Utilidades.TABLA_VENTAS +
                    " WHERE " +
                    Utilidades.CAMPO_PERSONA_VENTA + " = " + Utilidades.CAMPO_ID_PERSONA +
                    " AND " +
                    Utilidades.CAMPO_RESPALDO_VENTAS + " = " + 0;

    public static final String CREAR_VISTA_VENTAS = "CREATE VIEW " + VIEW_VENTAS + " AS " + SELECT_VENTA;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista Animales sin dueño
    public static final String VIEW_ANIMAL_NO_OWNER = "animal_view";

    public static final String SELECT_ANIMAL =
            "SELECT DISTINCT " +
                    Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                    Utilidades.CAMPO_GANADO + ", " +
                    Utilidades.CAMPO_RAZA + ", " +
                    Utilidades.CAMPO_PESO + ", " +
                    Utilidades.CAMPO_PRECIO + ", " +
                    Utilidades.CAMPO_TARA + ", " +
                    Utilidades.CAMPO_TOTAL_PAGAR + ", " +
                    Utilidades.CAMPO_NUMERO_ARETE + ", " +

                    Utilidades.CAMPO_ID_GANADO + ", " +
                    Utilidades.CAMPO_TIPO_GANADO + ", " +

                    Utilidades.CAMPO_ID_RAZA + ", " +
                    Utilidades.CAMPO_TIPO_RAZA +
                    " FROM " +
                    Utilidades.TABLA_COMPRA_DETALLE + ", " +
                    Utilidades.TABLA_GANADO + ", " +
                    Utilidades.TABLA_RAZA +
                    " WHERE " +
                    Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                    " AND " +
                    Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                    " AND " +
                    Utilidades.CAMPO_COMPRA + " IS NULL";

    public static final String CREAR_VISTA_ANIMAL = "CREATE VIEW " + VIEW_ANIMAL_NO_OWNER + " AS " + SELECT_ANIMAL;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista Animales en Venta sin dueño
    public static final String VIEW_ANIMAL_SALE_NO_OWNER = "animal_view_sale";

    public static final String SELECT_ANIMAL_SALE =
            "SELECT DISTINCT " +
                    Utilidades.CAMPO_ID_PERSONA + ", " +
                    Utilidades.CAMPO_NOMBRE + ", " +
                    Utilidades.CAMPO_TELEFONO + ", " +
                    Utilidades.CAMPO_DOMICILIO + ", " +
                    Utilidades.CAMPO_DATOS_EXTRAS + ", " +

                    Utilidades.CAMPO_ID_VENTA_DETALLE + ", " +
                    Utilidades.CAMPO_COMPRA_GANADO + ", " +
                    Utilidades.CAMPO_PRECIO_VENTA  + ", " +
                    Utilidades.CAMPO_TARA_VENTA + ", " +
                    Utilidades.CAMPO_TOTAL_VENTA + ", " +

                    Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                    Utilidades.CAMPO_GANADO + ", " +
                    Utilidades.CAMPO_RAZA + ", " +
                    Utilidades.CAMPO_PESO + ", " +
                    Utilidades.CAMPO_PRECIO + ", " +
                    Utilidades.CAMPO_TARA + ", " +
                    Utilidades.CAMPO_TOTAL_PAGAR + ", " +
                    Utilidades.CAMPO_NUMERO_ARETE + ", " +

                    Utilidades.CAMPO_ID_GANADO + ", " +
                    Utilidades.CAMPO_TIPO_GANADO + ", " +

                    Utilidades.CAMPO_ID_RAZA + ", " +
                    Utilidades.CAMPO_TIPO_RAZA +
                    " FROM " +
                    Utilidades.TABLA_PERSONA + ", " +
                    Utilidades.TABLA_VENTA_DETALLE + ", " +
                    Utilidades.TABLA_COMPRAS + ", " +
                    Utilidades.TABLA_COMPRA_DETALLE + ", " +
                    Utilidades.TABLA_GANADO + ", " +
                    Utilidades.TABLA_RAZA +
                    " WHERE " +
                    Utilidades.CAMPO_PERSONA_COMPRO + " = " + CAMPO_ID_PERSONA +
                    " AND " +
                    Utilidades.CAMPO_COMPRA + " = " + Utilidades.CAMPO_ID_COMPRA +
                    " AND " +
                    Utilidades.CAMPO_COMPRA_GANADO + " = " + CAMPO_ID_COMPRA_DETALLE +
                    " AND " +
                    Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                    " AND " +
                    Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                    " AND " +
                    Utilidades.CAMPO_VENTA + " IS NULL";

    public static final String CREAR_VISTA_ANIMAL_VENTA = "CREATE VIEW " + VIEW_ANIMAL_SALE_NO_OWNER + " AS " + SELECT_ANIMAL_SALE;
}
