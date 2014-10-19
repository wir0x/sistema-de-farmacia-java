
create table cliente(
cod_clt int identity (1,1)primary key,
nombrec varchar(15) not null,
paternoc varchar(20) not null,
maternoc varchar(20),
direccionc varchar(30),
telefonoc int ,
correoc varchar(20)
)
GO
create table empleado(
cod_emp int identity (1,1)primary key,
nombree varchar(30) not null,
paternoe varchar(30) not null,
maternoe varchar(30),
direccione varchar(30)not null,
telefonoe int
)
GO

create table categoria(
cod_cat int identity (1,1)primary key,
nombrec varchar(30) not null
)
GO
create table laboratorio(
cod_lab int identity (1,1) primary key,
nombrel varchar(20) not null,
direccionl varchar(30) not null,
telefonol char (12)not null,
emaill varchar(30),
webl varchar(30),
)
GO

create table ajuste(
cod_ajt int identity (1,1)primary key,
fecha smalldatetime not null,
observacion varchar(200) not null
)
GO
create table venta(
nro_venta int identity (1,1) primary key,
fecha smalldatetime,
cod_clt int not null,
cod_emp int not null,
foreign key (cod_clt) references cliente (cod_clt),
foreign key (cod_emp) references empleado(cod_emp)
)
GO
create table medicamento(
cod_med int identity (1,1)primary key,
nombrem varchar(30) not null,
preciom money not null,
stockm int not null,
cod_cat int 
foreign key (cod_cat) references categoria(cod_cat)
)

create table detalleVenta(
nro_venta int not null,
cod_med int not null,
cantidadv int not null,
preciov money not null,
primary key(nro_venta,cod_med),
foreign key (nro_venta) references venta(nro_venta),
foreign key (cod_med) references medicamento(cod_med)
)
GO
create table proveedor(
cod_provee int identity (1,1)primary key,
nombrep varchar(20) not null,
nitp varchar(30)not null,
direccionp varchar(30),
telefonop int not null,
cod_lab int not null,
foreign key(cod_lab) references laboratorio(cod_lab)
)
GO
create table compra(
cod_compra int identity (1,1)primary key,
fecha smalldatetime not null,
cod_provee int not null,
cod_emp int not null,
foreign key (cod_provee) references proveedor(cod_provee),
foreign key (cod_emp) references empleado(cod_emp)
)
GO
create table detalleCompra(
cod_compra int not null,
cod_med int not null,
cantidadc int not null,
precioc money not null,
primary key(cod_compra,cod_med),
foreign key(cod_compra) references compra(cod_compra),
foreign key(cod_med) references medicamento (cod_med)
)
GO
create table detalleAjuste(
cod_ajt int not null, 
cod_med int not null,
cantidada int not null,
primary key(cod_ajt,cod_med),
foreign key(cod_ajt) references ajuste(cod_ajt),
foreign key(cod_med) references medicamento(cod_med)
)
GO
create table ubicacion(
cod_med int not null,
cod_lab int not null,
primary key(cod_med,cod_lab),
foreign key(cod_med) references medicamento(cod_med),
foreign key(cod_lab) references laboratorio (cod_lab)
)
GO

-- PROCEDIMIENTO ALMACENADOS
-- CLIENTE 

create procedure insertarCliente
	@nombre varchar(15),
	@paterno varchar(20),
	@materno varchar(20),
	@direccion varchar(30),
	@telefono int ,
	@correo varchar (20)
as
begin 
	insert into cliente (nombrec,paternoc,maternoc,direccionc,telefonoc,correoc)
	values(@nombre,@paterno,@materno,@direccion,@telefono,@correo);
end;
create procedure eliminarCliente
@cod_clt int 
as
	if exists ( select *from  cliente where cod_clt=@cod_clt )
begin 
	delete from cliente where cod_clt=@cod_clt
end;

create procedure modificarCliente
	@cod_clt int,
	@nombre varchar(15),
	@paterno varchar(20),
	@materno varchar(20),
	@direccion varchar(30),
	@telefono int ,
	@correo varchar(20)
as
BEGIN
	if exists (select *from cliente where cod_clt=@cod_clt)
begin
	update cliente set
		nombrec=@nombre, 
		paternoc=@paterno,
		maternoc=@materno, 
		direccionc=@direccion, 
		telefonoc=@telefono, 
		correoc=@correo
	where cod_clt=@cod_clt;
end;
	else 
		RAISERROR ('NO PUEDE MODIFICAR POR QUE NO EXISTE ESE CLIENTE',10,1)
END;

-- MEDICAMENTO 
create procedure insertarMedicamento
	@nombre varchar (30),
	@precio money,
	@stock int,
	@cod_cat int
as
begin 
	insert into medicamento (nombrem,preciom,stockm,cod_cat)
	values (@nombre,@precio,@stock,@cod_cat)
end;

create procedure eliminarMedicamento 
@cod_med int 
as
	if exists (select *from medicamento where cod_med=@cod_med)and 
	not exists (select nro_venta from detalleVenta where detalleVenta.cod_med=@cod_med)
begin 
	delete from medicamento where cod_med =@cod_med
end;

create procedure modificarMedicamento 
	@cod_med int,
	@nombre varchar(30),
	@precio money,
	@stock int,
	@cod_cat int 
as
BEGIN
	if exists (select *from medicamento where cod_med=@cod_med)
	begin 
		update medicamento set
			nombrem=@nombre,
			preciom=@precio,
			stockm=@stock,
			cod_cat=@cod_cat
		where cod_med = @cod_med
end;
	else
		RAISERROR ('NO ES POSIBLE REALIZAR LA MODIFICACION, PORQUE NO EXISTE EL CLIENTE',30,10)
END;
-- VENTA 

create procedure insertarVenta
	@fecha 	smalldatetime,
	@cod_clt int ,
	@cod_emp int 
as
begin 
	insert into venta (fecha, cod_clt, cod_emp)
	values (@fecha, @cod_clt,@cod_emp)
end;

create procedure insertarDetalleVenta
	@cod_med int ,
	@precio money,
	@cantidad int
as
declare @nro_venta int 
begin 
	select @nro_venta=Max(nro_venta) from  venta
	insert detalleVenta values (@nro_venta,@cod_med,@precio,@cantidad)
	update medicamento set stockm=stockm-@cantidad where cod_med =@cod_med
end;


CREATE PROCEDURE INSERTAR_VENTA
@cod_clt int,
@cod_emp int
AS
BEGIN
	insert into venta values(GETDATE(),@cod_clt,@cod_emp);
END;

CREATE PROCEDURE INSERTA_DV
@cod_med int,
@cantidad int,
@precio money
AS
declare @cod_venta int
BEGIN
	SELECT  @cod_venta = MAX(nro_venta) from venta
	insert into detalleVenta values(@cod_venta,@cod_med,@cantidad,@precio)
	UPDATE medicamento SET stockm = stockm-@cantidad where cod_med = @cod_med;
END;

-- LABORATORIO
create procedure insertarLaboratorio
	@cod_lab int,
	@nombre varchar(20),
	@direccion varchar(30),
	@telefono varchar(12),
	@email varchar(30),
	@web varchar(30)
as
begin 
	insert into laboratorio(nombrel,direccionl,telefonol,emaill,webl)
	values (@nombre,@direccion,@telefono,@email,@web)
end;




--CATEGORIA

create procedure insertarCategoria
	@nombre varchar(30)
as
begin 
	insert into categoria (nombrec)
	values (@nombre);
end;


create procedure elimarCategoria
@cod_cat int 
as
	if exists ( select *from categoria where cod_cat=@cod_cat)
begin 
	delete from categoria where cod_cat=@cod_cat
end; 

create procedure modificarCategoria
	@cod_cat int,
	@nombre varchar(30)
as
	
BEGIN
	if exists (select *from categoria where cod_cat=@cod_cat)
	begin 
		update categoria set 
			nombrec=@nombre	
			where cod_cat = @cod_cat;
	end;
		else 
			RAISERROR ('NO SE PUEDE MODIFCAR, PORQUE NO EXISTE ESA CATEGORIA',30,10)
END;


-- EMPLEADO

create procedure insertarEmpleado
	@nombre varchar(30),
	@paterno varchar(30),
	@materno varchar(30),
	@direccion varchar(30),
	@telefono int
as
begin 
	insert into empleado (nombree,paternoe,maternoe,direccione,telefonoe)
	values (@nombre,@paterno,@materno,@direccion,@telefono)
end;

create procedure eliminarEmpleado
@cod_emp int
as
	if exists (select *from empleado where cod_emp=@cod_emp)

begin
	delete empleado where cod_emp=@cod_emp
end;

create procedure modificarEmpleado
@cod_emp int,
@nombre varchar(30),
@paterno varchar(30),
@materno varchar(30),
@direccion varchar(30),
@telefono int
as
begin 
	if exists (select *from empleado where cod_emp=@cod_emp)
	begin 
		update empleado set
		nombree=@nombre,
		paternoe=@paterno,
		maternoe=@materno,
		direccione=@direccion,
		telefonoe=@telefono
		where cod_emp = @cod_emp;
	end;
	else
		RAISERROR ('IMPOSIBLE MODIFICA, EMPLEADO NO EXISTE',30,10)
end;


--OTROS
select *from sys.databases
select *from sys.sysobjects where name like '%in%'
select *from sys.sysobjects where name like '%insertar%'
select *from venta
select *from empleado
select *from detalleVenta
select *from cliente
select *from detalleVenta
select *from laboratorio
select *from categoria
select *from medicamento
select *from proveedor
insert into empleado values('Alejandra','Mancilla','Cordoba','C/Challapa',75316458)

--CATEGORIAS
insert into categoria values ('Analgesicos')
insert into categoria values ('Antinflamatorios')
insert into categoria values ('Aines')
insert into categoria values ('Antibioticos')
insert into categoria values ('Amoxicilna')
insert into categoria values ('Cefalosporinas')
insert into categoria values ('Eritromicina')
insert into categoria values ('Quinolona')
insert into categoria values ('Ampicilina')
insert into categoria values ('Vancomicina')
insert into categoria values ('Azitromicina')
insert into categoria values ('Penicilinas')
--MEDICAMENTOS
insert into medicamento values ('Asgesic 10mg',2,80,1)
insert into medicamento values ('Asgesic 20mg',3.5,95,1)
insert into medicamento values ('Asgesic 30mg',5,74,1)
insert into medicamento values ('Asgesic 60mg',8.5,65,1)
insert into medicamento values ('Dolalgial',3,76,1)
insert into medicamento values ('Naclodil',5,43,1)
insert into medicamento values ('Dolostop 20mg',6,54,1)
insert into medicamento values ('Aspirina',1,99,1)
insert into medicamento values ('Cafiaspirina',1,65,1)
insert into medicamento values ('Dolpiret Cmp',2,54,1)
insert into medicamento values ('Dolpiret Ssp',3,64,1)
insert into medicamento values ('Dontoflamon Cmp',2.5,13,1)
insert into medicamento values ('Dontoflamon Ssp',3.5,65,1)
insert into medicamento values ('Ibuflamar',6.5,75,1)
insert into medicamento values ('Piredol',5,48,1)
insert into medicamento values ('Flamadin',3,46,2)
insert into medicamento values ('Diprofen Cmp',6,84,2)
insert into medicamento values ('Diprofen Ssp',7.5,46,2)
insert into medicamento values ('Ibupronal Forte',10,64,2)
insert into medicamento values ('Ceflex 200mg',4.5,76,2)
insert into medicamento values ('Flamax 15mg',2.5,56,2)
insert into medicamento values ('Flexidol 100mg',6.5,87,2)
insert into medicamento values ('Flexidol 200mg',11,46,2)
insert into medicamento values ('Sigma Bron 100mg',8.5,63,2)
insert into medicamento values ('Argifen 400mg',15,36,2)
insert into medicamento values ('Acefen',6,84,2)
insert into medicamento values ('Flamarion 60mg',9,86,2)
insert into medicamento values ('Analtin 200mg',7,46,2)
insert into medicamento values ('Medox',20,29,2)
insert into medicamento values ('Dolpiret Ssp',6.5,65,3)
insert into medicamento values ('Dolpiret Cmp',5,55,3)
insert into medicamento values ('Asgesic 10mg',2.5,25,3)
insert into medicamento values ('Asgesic 20mg',4,44,3)
insert into medicamento values ('Ibupronal',6,54,3)
insert into medicamento values ('Ibupronal Forte',11,31,3)
insert into medicamento values ('Flamax 15mg',6.5,65,3)
insert into medicamento values ('Flexidol 100mg',2.5,25,3)
insert into medicamento values ('Flexidol 200mg',4.5,45,3)
insert into medicamento values ('Terbocil 6 Benzatinica',20,100,12)
insert into medicamento values ('Terbocil 3 Procainica',15,10,12)
insert into medicamento values ('Terbocil 3 Sodica',15,30,12)
insert into medicamento values ('Terbocil Forte Benzatinica',30,100,12)
insert into medicamento values ('Terbocil Forte Procainica',20,50,12)
insert into medicamento values ('Terbocil Forte Sodica',20,45,12)
insert into medicamento values ('Terbocil 1.2MM Benzaticica',25,61,12)
insert into medicamento values ('Terbocil 2.4MM Benzatinica',35,43,12)
insert into medicamento values ('Ifacilina 6 Benzatinica',20,94,12)
insert into medicamento values ('ifacilina 3 Procainica',10,82,12)
insert into medicamento values ('Ifacilina 3 Sodica',10,65,12)
insert into medicamento values ('Ifacilina 12 Benzatinica',32,54,12)
insert into medicamento values ('Ifacilina 6 Procainica',20,65,12)
insert into medicamento values ('Ifacilina 6 Sodica',20,46,12)
insert into medicamento values ('Ifacilina 600',15,69,12)
insert into medicamento values ('Ifacilina 1.2M',25,35,12)
insert into medicamento values ('Ifacilina 2.4M',30,45,12)
insert into medicamento values ('Moxilin Duo Cmp',5,64,5)
insert into medicamento values ('Moxilin Duo Ssp',6,84,5)
insert into medicamento values ('Moxilin Plus Duo 875mg',8,48,5)
insert into medicamento values ('Moxilin Plus Duo 125mg',8,64,5)
insert into medicamento values ('Moxilin Plus Duo 1000mg',12,54,5)
insert into medicamento values ('Moxilin Plus Duo250mg',12,64,5)
insert into medicamento values ('Plamodex 875mg',21,95,5)
insert into medicamento values ('Amoxidal Duo',25,75,5)
insert into medicamento values ('Amoxicilina IFA 125mg',12.5,54,5)
insert into medicamento values ('Amoxicilina IFA 250mg',25,51,5)
insert into medicamento values ('Amoxicilina IFA 500mg Ssp',30,54,5)
insert into medicamento values ('Amoxicilina IFA 500mg Cmp',25,64,5)
insert into medicamento values ('Amoxicilina IFA 1g',45,65,5)
insert into medicamento values ('Broxilin 1g',40,51,5)
insert into medicamento values ('Broxilin 250mg',25,64,5)
insert into medicamento values ('Ampicilina IFA 125mg',12,32,9)
insert into medicamento values ('Ampicilina IFA 250mg',20,45,9)
insert into medicamento values ('Ampicilina IFA 500mg Ssp',30,55,9)
insert into medicamento values ('Ampicilina IFA 500mg',25,31,9)
insert into medicamento values ('Neo Terbocilin 1g',30,15,9)
insert into medicamento values ('Cefotrix 250mg',20,45,6)
insert into medicamento values ('Cefotrix 500mg',34,54,6)
insert into medicamento values ('Supracef 1g',35,55,6)
insert into medicamento values ('Terbodina 250mg',20,77,6)
insert into medicamento values ('Terbodina 500mg',45,66,6)
insert into medicamento values ('Ceftacef 1g',33,45,6)
insert into medicamento values ('Triacef 400mg',32,46,6)
insert into medicamento values ('Triacef 100mg',10,54,6)
insert into medicamento values ('Megacef 1g',40,66,6)
insert into medicamento values ('Eritrobol 500mg',45,65,7)
insert into medicamento values ('Eritrobol 250mg',26,48,7)
insert into medicamento values ('Vancobiotic 1g',35,46,10)
insert into medicamento values ('Vancobiotic 500mg',16,36,10)
insert into medicamento values ('Quinil 500mg',45,99,8)
insert into medicamento values ('Quinol 750mg',60,61,8)
insert into medicamento values ('Macrotron 200',20,56,11)
insert into medicamento values ('Macrotron Forte 500mg',55,64,11)
insert into medicamento values ('Macrotron 500mg',42,46,11)
insert into medicamento values ('Macrotron 1g',60,64,11)
insert into medicamento values ('Zitromin 500mg',39,31,11)
insert into medicamento values ('Laser',45,64,4)
insert into medicamento values ('Ciflox 500mg',36,65,4)
insert into medicamento values ('Ciprinol 500mg',40,64,4)
insert into medicamento values ('Dicloxin 500mg',37,64,4)




--UTILIDADES
select cod_provee, nombrep, nitp, direccionp, telefonop, nombrel from proveedor, laboratorio
where proveedor.cod_lab=laboratorio.cod_lab

select cod_med, nombrem, preciom, stockm, nombrec from medicamento, categoria
where medicamento.cod_cat=categoria.cod_cat



create procedure insertarProveedor
	@nombre varchar(15),
	@nit varchar(20),
	@direccion varchar(30),
	@telefono int ,
	@cod_lab int
as
begin 
	insert into proveedor(nombre,nit,direccion,telefono,cod_lab)
	values(@nombre,@nit,@direccion,@telefono,@cod_lab);
end;

create procedure eliminarProveedor
	@cod_provee int
as
if exists (select *from proveedor where cod_provee=@cod_provee)

begin
	delete proveedor where cod_provee=@cod_provee
end

create procedure modificarProveedor
@cod_provee int,
@nombre varchar(30),
@nit varchar(30),
@direccion varchar(30),
@telefono int,
@cod_lab int
as
begin 
	if exists (select *from proveedor where cod_provee=@cod_provee)
	begin 
		update proveedor set
		nombre=@nombre,
		nit=@nit,
		direccion=@direccion,
		telefono=@telefono,
		cod_lab=@cod_lab
		where cod_provee = @cod_provee;
	end;
	else
		RAISERROR ('IMPOSIBLE MODIFICA, PROVEEDOR NO EXISTE',30,10)
end;

create procedure insertar_dcompra
@cod_med int,
@cantidad int,
@precio money
AS
declare @cod_compra int
BEGIN
	SELECT  @cod_compra = MAX(cod_compra) from compra
	insert into detalleCompra values(@cod_compra,@cod_med,@cantidad,@precio)
	UPDATE medicamento SET stock = stock+@cantidad where cod_med = @cod_med;
END;

CREATE PROCEDURE insertar_compra
@cod_provee int,
@cod_emp int
AS
BEGIN
	insert into compra values(GETDATE(),@cod_provee,@cod_emp);
END






create procedure insertarCategoria
	@nombre varchar(30)
as
begin 
	insert into categoria(nombrec)
	values (@nombre)
end;

create procedure eliminarCategoria
@cod_cat int
as
	if exists (select *from categoria where cod_cat=@cod_cat)

begin
	delete categoria where cod_cat=@cod_cat
end;

create procedure modificarCategoria
@cod_cat int,
@nombre varchar(30)
as
begin 
	if exists (select *from categoria where cod_cat=@cod_cat)
	begin 
		update categoria set
		nombrec=@nombre
		where cod_cat = @cod_cat;
	end;
	else
		RAISERROR ('IMPOSIBLE MODIFICA, PROVEEDOR NO EXISTE',30,10)
end;