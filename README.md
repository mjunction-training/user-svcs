# user-svcs
Microservice training project

Generate self signed certificate

keytool -genkey -keyalg RSA -dname "cn=MJunction Training, ou=ExpoGrow, o=ExpoGrow.org, l=Kolkata, s=W.B., c=IN" -alias selfsigned -keystore selfsigned.jks -storepass password -validity 360 -keysize 2048  -deststoretype pkcs12

Export public key (password :: 'password')
keytool -export -alias selfsigned -keystore selfsigned.jks -file selfsigned-public.pem


Create below tables and data in the database:

Execute 'maridb-schema.sql'





