package com.example.appganaderosv1.utilidades;

public class Utilidades {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla usuario
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_ID_USUARIO = "id_usuario";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_CONTRASENA = "contrasena";
    public static final String CAMPO_TIPO_USUARIO = "tipo_usuario";
    public static final String CAMPO_ACTIVO = "activo";

    public static final String CREAR_TABLA_USUARIO =
            "CREATE TABLE " + TABLA_USUARIO + "("
                    + CAMPO_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_USUARIO + " TEXT, "
                    + CAMPO_CONTRASENA + " TEXT, "
                    + CAMPO_TIPO_USUARIO + " TEXT, "
                    + CAMPO_ACTIVO + " INTEGER)";
    public static final String INSERTAR_USUARIOS =
            "INSERT INTO usuario " +
                    "(usuario, contrasena, tipo_usuario, activo)" +
                    " values " +
                    "('Felipe', '123', 'Normal', 0)," +
                    "('Admin', 'admin', 'Administrador', 0)";


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
    public static final String CAMPO_COMPRA_PAGADA = "pagado";
    public static final String CAMPO_RESPALDO_COMPRAS = "respaldo";

    public static final String CREAR_TABLA_COMPRAS =
            "CREATE TABLE " + TABLA_COMPRAS + "("
                    + CAMPO_ID_COMPRA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_PERSONA_COMPRO + " INTEGER REFERENCES " + TABLA_PERSONA + "(" + CAMPO_ID_PERSONA + ") ON DELETE CASCADE, "
                    + CAMPO_FECHA_COMPRAS + " TEXT, "
                    + CAMPO_CANTIDAD_ANIMALES_COMPRAS + " INTEGER, "
                    + CAMPO_CANTIDAD_PAGAR + " REAL, "
                    + CAMPO_COMPRA_PAGADA + " BOOLEAN, "
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

    public static final String INSERT_RAZA =
            "INSERT INTO " + TABLA_RAZA + "(" + CAMPO_TIPO_RAZA + ") VALUES " +
                    "('Angus')," +
                    "('Jersey')," +
                    "('Charolesa')," +
                    "('Simmental')," +
                    "('Pardo Suizo')," +
                    "('Angus Rojo')," +
                    "('Limousin')," +
                    "('Pardo Suizo')";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Ganado
    public static final String TABLA_GANADO = "ganado";
    public static final String CAMPO_ID_GANADO = "id_ganado";
    public static final String CAMPO_TIPO_GANADO = "tipo_ganado";

    public static final String CREAR_TABLA_GANADO =
            "CREATE TABLE " + TABLA_GANADO + "("
                    + CAMPO_ID_GANADO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_TIPO_GANADO + " TEXT)";

    public static final String INSERT_GANADO =
            "INSERT INTO " + TABLA_GANADO + "(" + CAMPO_TIPO_GANADO + ") VALUES " +
                    "('Toro')," +
                    "('Vaca')," +
                    "('Becerro')," +
                    "('Beccerra')";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Compra Detalle
    public static final String TABLA_COMPRA_DETALLE = "compra_detalle";
    public static final String CAMPO_ID_COMPRA_DETALLE = "id_compra_detalle";
    public static final String CAMPO_GANADO = "ganado";
    public static final String CAMPO_RAZA = "raza";
    public static final String CAMPO_PESO_PIE_COMPRA = "peso_pie_compra";
    public static final String CAMPO_PESO_CANAL_COMPRA = "peso_canal_compra";
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
                    + CAMPO_PESO_PIE_COMPRA + " REAL, "
                    + CAMPO_PESO_CANAL_COMPRA + " REAL, "
                    + CAMPO_PRECIO + " REAL, "
                    + CAMPO_TARA + " REAL, "
                    + CAMPO_TOTAL_PAGAR + " REAL, "
                    + CAMPO_NUMERO_ARETE + " TEXT, "
                    + CAMPO_COMPRA + " INTEGER REFERENCES " + TABLA_COMPRAS + "(" + CAMPO_ID_COMPRA + ") ON DELETE CASCADE)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Ventas
    public static final String TABLA_VENTAS = "ventas";
    public static final String CAMPO_ID_VENTAS = "id_ventas";
    public static final String CAMPO_PERSONA_VENTA = "persona_venta";
    public static final String CAMPO_FECHA_VENTAS = "fecha";
    public static final String CAMPO_CANTIDAD_ANIMALES_VENTAS = "cantidad_animales";
    public static final String CAMPO_CANTIDAD_COBRAR = "cantidad_cobrar";
    public static final String CAMPO_GANANCIAS = "ganancias";
    public static final String CAMPO_VENTA_PAGADA = "pagado";
    public static final String CAMPO_RESPALDO_VENTAS = "respaldo";

    public static final String CREAR_TABLA_VENTAS =
            "CREATE TABLE " + TABLA_VENTAS + "("
                    + CAMPO_ID_VENTAS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_PERSONA_VENTA + " INTEGER REFERENCES " + TABLA_PERSONA + "(" + CAMPO_ID_PERSONA + ") ON DELETE CASCADE, "
                    + CAMPO_FECHA_VENTAS + " TEXT, "
                    + CAMPO_CANTIDAD_ANIMALES_VENTAS + " INTEGER, "
                    + CAMPO_CANTIDAD_COBRAR + " REAL, "
                    + CAMPO_GANANCIAS + " REAL, "
                    + CAMPO_VENTA_PAGADA + " BOOLEAN, "
                    + CAMPO_RESPALDO_VENTAS + " INTEGER)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Venta Detalle
    public static final String TABLA_VENTA_DETALLE = "venta_detalle";
    public static final String CAMPO_ID_VENTA_DETALLE = "id_venta_detalle";
    public static final String CAMPO_COMPRA_GANADO = "compra_animal";
    public static final String CAMPO_PESO_CANAL_VENTA = "peso_canal_venta";
    public static final String CAMPO_PRECIO_VENTA = "precio_venta";
    public static final String CAMPO_TARA_VENTA = "tara_venta";
    public static final String CAMPO_TOTAL_VENTA = "total_ct";
    public static final String CAMPO_VENTA = "venta";

    public static final String CREAR_TABLA_VENTA_DETALLE =
            "CREATE TABLE " + TABLA_VENTA_DETALLE + "("
                    + CAMPO_ID_VENTA_DETALLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_COMPRA_GANADO + " INTEGER REFERENCES " + TABLA_COMPRA_DETALLE + "(" + CAMPO_ID_COMPRA_DETALLE + "), "
                    + CAMPO_PESO_CANAL_VENTA + " REAL, "
                    + CAMPO_PRECIO_VENTA + " REAL, "
                    + CAMPO_TARA_VENTA + " REAL, "
                    + CAMPO_TOTAL_VENTA + " REAL, "
                    + CAMPO_VENTA + " INTEGER NULL REFERENCES " + TABLA_VENTAS + "(" + CAMPO_ID_VENTAS + ") ON DELETE CASCADE)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Bitacora
    public static final String TABLA_BITACORA = "bitacora";
    public static final String CAMPO_ID_BITACORA = "id_bit";
    public static final String CAMPO_ID_USUARIO_BIT = "id_usuario";
    public static final String CAMPO_ID_CITA_BIT = "id_cita";
    public static final String CAMPO_ID_COMPRA_BIT = "id_compra";
    public static final String CAMPO_ID_VENTA_BIT = "id_venta";
    public static final String CAMPO_ACCION = "action";
    public static final String CAMPO_FECHA = "date";

    public static final String CREAR_TABLA_BITACORA =
            "CREATE TABLE " + TABLA_BITACORA + " ("
                    + CAMPO_ID_BITACORA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_ID_USUARIO_BIT + " INTEGER REFERENCES " + TABLA_USUARIO + " (" + CAMPO_ID_USUARIO + "), "
                    + CAMPO_ID_CITA_BIT + " INTEGER REFERENCES " + TABLA_CITAS + " ("+ CAMPO_ID_CITAS +") ON DELETE CASCADE, "
                    + CAMPO_ID_COMPRA_BIT + " INTEGER REFERENCES " + TABLA_COMPRAS +" (" + CAMPO_ID_COMPRA + ") ON DELETE CASCADE, "
                    + CAMPO_ID_VENTA_BIT + " INTEGER REFERENCES "+ TABLA_VENTAS +" (" + CAMPO_ID_VENTAS + ") ON DELETE CASCADE, "
                    + CAMPO_ACCION + " TEXT NOT NULL, "
                    + CAMPO_FECHA + " DATE NOT NULL)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista de citas
    public static final String VIEW_CITA = "appointment_view";

    public static final String SELECT_CITA =
            "SELECT " +
                    Utilidades.CAMPO_ID_CITAS + ", " +
                    Utilidades.CAMPO_CANTIDAD_GANADO + ", " +
                    Utilidades.CAMPO_DATOS + ", " +
                    Utilidades.CAMPO_FECHA_CITAS + ", " +
                    Utilidades.CAMPO_RESPALDO_CITAS + ", " +

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
                    Utilidades.CAMPO_RESPALDO_CITAS + " = " + 0 +
                    " ORDER BY " +
                    "DATE(" + Utilidades.CAMPO_FECHA_CITAS + ")";

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
                    Utilidades.CAMPO_COMPRA_PAGADA + ", " +
                    Utilidades.CAMPO_RESPALDO_COMPRAS + ", " +

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
                    Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 0 +
                    " ORDER BY " +
                    "DATE(" + Utilidades.CAMPO_FECHA_COMPRAS + ")";

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
                    Utilidades.CAMPO_VENTA_PAGADA + ", " +
                    Utilidades.CAMPO_RESPALDO_VENTAS + ", " +

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
                    Utilidades.CAMPO_RESPALDO_VENTAS + " = " + 0 +
                    " ORDER BY " +
                    "DATE(" + Utilidades.CAMPO_FECHA_VENTAS + ")";

    public static final String CREAR_VISTA_VENTAS = "CREATE VIEW " + VIEW_VENTAS + " AS " + SELECT_VENTA;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Vista Animales sin dueño
    public static final String VIEW_ANIMAL_NO_OWNER = "animal_view";

    public static final String SELECT_ANIMAL =
            "SELECT DISTINCT " +
                    Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                    Utilidades.CAMPO_GANADO + ", " +
                    Utilidades.CAMPO_RAZA + ", " +
                    Utilidades.CAMPO_PESO_PIE_COMPRA + ", " +
                    Utilidades.CAMPO_PESO_CANAL_COMPRA + ", " +
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
                    Utilidades.CAMPO_PESO_CANAL_VENTA + ", " +
                    Utilidades.CAMPO_PRECIO_VENTA + ", " +
                    Utilidades.CAMPO_TARA_VENTA + ", " +
                    Utilidades.CAMPO_TOTAL_VENTA + ", " +

                    Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                    Utilidades.CAMPO_GANADO + ", " +
                    Utilidades.CAMPO_RAZA + ", " +
                    Utilidades.CAMPO_PESO_PIE_COMPRA + ", " +
                    Utilidades.CAMPO_PESO_CANAL_COMPRA + ", " +
                    Utilidades.CAMPO_PRECIO + ", " +
                    Utilidades.CAMPO_TARA + ", " +
                    Utilidades.CAMPO_TOTAL_PAGAR + ", " +
                    Utilidades.CAMPO_NUMERO_ARETE + ", " +

                    Utilidades.CAMPO_ID_GANADO + ", " +
                    Utilidades.CAMPO_TIPO_GANADO + ", " +

                    Utilidades.CAMPO_ID_RAZA + ", " +
                    Utilidades.CAMPO_TIPO_RAZA + ", " +

                    Utilidades.CAMPO_FECHA_COMPRAS +
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


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Sumar Cantidad Animales en Compras
    public static final String TRIGGER_PURCHASE_ANIMALS = "add_quantity_animals_purchase";

    public static final String TRIGGER_ACTION_PURCHASE =
            "UPDATE compras SET cantidad_animales = (cantidad_animales + 1) WHERE id_compras = new.compra";

    public static final String CREAR_TRIGGER_COMPRA_ANIMALES =
            "CREATE TRIGGER " + TRIGGER_PURCHASE_ANIMALS + " BEFORE INSERT ON " + TABLA_COMPRA_DETALLE +
                    " BEGIN " +
                    TRIGGER_ACTION_PURCHASE + ";" +
                    " END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Restar Cantidad Animales en Compras
    public static final String TRIGGER_SUBTRACT_PURCHASE_ANIMALS = "subtract_quantity_animals_purchase";

    public static final String TRIGGER_SUBTRACT_ACTION_PURCHASE =
            "UPDATE compras SET cantidad_animales = (cantidad_animales - 1) WHERE id_compras = old.compra";

    public static final String CREAR_TRIGGER_COMPRA_ANIMALES_RESTAR =
            "CREATE TRIGGER " + TRIGGER_SUBTRACT_PURCHASE_ANIMALS + " BEFORE DELETE ON " + TABLA_COMPRA_DETALLE +
                    " BEGIN " +
                    TRIGGER_SUBTRACT_ACTION_PURCHASE + ";" +
                    " END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Sumar Cantidad Animales en Ventas
    public static final String TRIGGER_SALE_ANIMALS = "add_quantity_animals_sale";

    public static final String TRIGGER_ACTION_SALE =
            "UPDATE ventas SET cantidad_animales = (cantidad_animales + 1) WHERE id_ventas = new.venta";

    public static final String CREAR_TRIGGER_VENTA_ANIMALES =
            "CREATE TRIGGER " + TRIGGER_SALE_ANIMALS + " BEFORE INSERT ON " + TABLA_VENTA_DETALLE +
                    " BEGIN " +
                    TRIGGER_ACTION_SALE + ";" +
                    " END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Restar Cantidad Animales en Ventas
    public static final String TRIGGER_SUBTRACT_SALE_ANIMALS = "subtract_quantity_animals_sale";

    public static final String TRIGGER_SUBTRACT_ACTION_SALE =
            "UPDATE ventas SET cantidad_animales = (cantidad_animales - 1) WHERE id_ventas = old.venta";

    public static final String CREAR_TRIGGER_VENTA_ANIMALES_RESTAR =
            "CREATE TRIGGER " + TRIGGER_SUBTRACT_SALE_ANIMALS + " BEFORE DELETE ON " + TABLA_VENTA_DETALLE +
                    " BEGIN " +
                    TRIGGER_SUBTRACT_ACTION_SALE + ";" +
                    " END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Citas Bitacora
    public static  final String TRIGGER_INSERT_APPOINTMENT_BIT = "cita_bit";

    public static final String TRIGGER_INSERT_APPOINTMENT_BIT_ACTION =
            "INSERT INTO bitacora (id_usuario, id_cita, action, date)" +
                    "values (" +
                        "(SELECT id_usuario FROM " + TABLA_USUARIO + " WHERE activo = 1)," +
                        "new.id_cita," +
                        "'insert'," +
                        "(select datetime('now'))" +
                        ")";

    public static final String CREAR_TRIGGER_INSERT_APPOINTMENT_BIT =
            "CREATE TRIGGER "+ TRIGGER_INSERT_APPOINTMENT_BIT + " AFTER INSERT ON "+ TABLA_CITAS +
                    " BEGIN " +
                    TRIGGER_INSERT_APPOINTMENT_BIT_ACTION + ";" +
                    "END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Compras Bitacora
    public static  final String TRIGGER_INSERT_PURCHASE_BIT = "compra_bit";

    public static final String TRIGGER_INSERT_PURCHASE_BIT_ACTION =
            "INSERT INTO bitacora (id_usuario, id_compra, action, date)" +
                    "values (" +
                    "(SELECT id_usuario FROM " + TABLA_USUARIO + " WHERE activo = 1)," +
                    "new.id_compras," +
                    "'insert'," +
                    "(select datetime('now'))" +
                    ")";

    public static final String CREAR_TRIGGER_INSERT_PURCHASE_BIT =
            "CREATE TRIGGER "+ TRIGGER_INSERT_PURCHASE_BIT + " AFTER INSERT ON "+ TABLA_COMPRAS +
                    " BEGIN " +
                    TRIGGER_INSERT_PURCHASE_BIT_ACTION + ";" +
                    "END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Trigger Compras Bitacora
    public static  final String TRIGGER_INSERT_SALE_BIT = "venta_bit";

    public static final String TRIGGER_INSERT_SALE_BIT_ACTION =
            "INSERT INTO bitacora (id_usuario, id_venta, action, date)" +
                    "values (" +
                    "(SELECT id_usuario FROM " + TABLA_USUARIO + " WHERE activo = 1)," +
                    "new.id_ventas," +
                    "'insert'," +
                    "(select datetime('now'))" +
                    ")";

    public static final String CREAR_TRIGGER_INSERT_SALE_BIT =
            "CREATE TRIGGER "+ TRIGGER_INSERT_SALE_BIT + " AFTER INSERT ON "+ TABLA_VENTAS +
                    " BEGIN " +
                    TRIGGER_INSERT_SALE_BIT_ACTION + ";" +
                    "END";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Index Purchase Table
    public static final String INDEX_TABLE_PURCHASES = "purchaseTable";

    public static final String CREATE_INDEX_TABLE_PURCHASES =
            "CREATE INDEX " + INDEX_TABLE_PURCHASES + " ON " + TABLA_COMPRAS + "(persona_compro, cantidad_animales, fecha)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Index Sales Table
    public static final String INDEX_TABLE_SALES = "salesTable";

    public static final String CREATE_INDEX_TABLE_SALES =
            "CREATE INDEX " + INDEX_TABLE_SALES + " ON " + TABLA_VENTAS + "(persona_venta, cantidad_animales, fecha)";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Index Appointment Table
    public static final String INDEX_TABLE_APPOINTMENT = "appointmentTable";

    public static final String CREATE_INDEX_TABLE_APPOINTMENT =
            "CREATE INDEX " + INDEX_TABLE_APPOINTMENT + " ON " + TABLA_CITAS + "(persona_cita, cantidad_ganado, fecha)";
}
