PGDMP     ,    	                |            veterinaryDB    15.6    15.6 7    :           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ;           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            <           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            =           1262    16938    veterinaryDB    DATABASE     �   CREATE DATABASE "veterinaryDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "veterinaryDB";
                postgres    false            �            1259    17141    animals    TABLE     �  CREATE TABLE public.animals (
    animal_id bigint NOT NULL,
    breed character varying(255),
    animal_colour character varying(255),
    animal_date_of_birth date,
    animal_gender character varying(255),
    animal_name character varying(255) NOT NULL,
    animal_species character varying(255) NOT NULL,
    customer_id bigint,
    CONSTRAINT animals_animal_gender_check CHECK (((animal_gender)::text = ANY ((ARRAY['erkek'::character varying, 'dişi'::character varying])::text[])))
);
    DROP TABLE public.animals;
       public         heap    postgres    false            �            1259    17140    animals_animal_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.animals_animal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.animals_animal_id_seq;
       public          postgres    false    215            >           0    0    animals_animal_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.animals_animal_id_seq OWNED BY public.animals.animal_id;
          public          postgres    false    214            �            1259    17151    appointments    TABLE     �   CREATE TABLE public.appointments (
    appointment_id bigint NOT NULL,
    appointment_date timestamp(6) without time zone,
    appointment_animal_id bigint,
    appointment_doctor_id bigint
);
     DROP TABLE public.appointments;
       public         heap    postgres    false            �            1259    17150    appointments_appointment_id_seq    SEQUENCE     �   CREATE SEQUENCE public.appointments_appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.appointments_appointment_id_seq;
       public          postgres    false    217            ?           0    0    appointments_appointment_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.appointments_appointment_id_seq OWNED BY public.appointments.appointment_id;
          public          postgres    false    216            �            1259    17158    availabledates    TABLE     �   CREATE TABLE public.availabledates (
    available_date_id bigint NOT NULL,
    available_date date,
    available_date_doctor_id bigint
);
 "   DROP TABLE public.availabledates;
       public         heap    postgres    false            �            1259    17157 $   availabledates_available_date_id_seq    SEQUENCE     �   CREATE SEQUENCE public.availabledates_available_date_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE public.availabledates_available_date_id_seq;
       public          postgres    false    219            @           0    0 $   availabledates_available_date_id_seq    SEQUENCE OWNED BY     m   ALTER SEQUENCE public.availabledates_available_date_id_seq OWNED BY public.availabledates.available_date_id;
          public          postgres    false    218            �            1259    17165 	   customers    TABLE     4  CREATE TABLE public.customers (
    customer_id bigint NOT NULL,
    customer_address character varying(255),
    customer_city character varying(255),
    customer_mail character varying(255) NOT NULL,
    customer_name character varying(255) NOT NULL,
    customer_phone character varying(255) NOT NULL
);
    DROP TABLE public.customers;
       public         heap    postgres    false            �            1259    17164    customers_customer_id_seq    SEQUENCE     �   CREATE SEQUENCE public.customers_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.customers_customer_id_seq;
       public          postgres    false    221            A           0    0    customers_customer_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.customers_customer_id_seq OWNED BY public.customers.customer_id;
          public          postgres    false    220            �            1259    17174    doctors    TABLE     &  CREATE TABLE public.doctors (
    doctor_id bigint NOT NULL,
    doctor_address character varying(255),
    doctor_city character varying(255),
    doctor_mail character varying(255) NOT NULL,
    doctor_name character varying(255) NOT NULL,
    doctor_phone character varying(255) NOT NULL
);
    DROP TABLE public.doctors;
       public         heap    postgres    false            �            1259    17173    doctors_doctor_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.doctors_doctor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.doctors_doctor_id_seq;
       public          postgres    false    223            B           0    0    doctors_doctor_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.doctors_doctor_id_seq OWNED BY public.doctors.doctor_id;
          public          postgres    false    222            �            1259    17183    vaccines    TABLE       CREATE TABLE public.vaccines (
    vaccine_id bigint NOT NULL,
    vaccine_code character varying(255) NOT NULL,
    vaccine_name character varying(255) NOT NULL,
    vaccine_protection_finish_date date,
    vaccine_protection_start_date date,
    animal_id bigint
);
    DROP TABLE public.vaccines;
       public         heap    postgres    false            �            1259    17182    vaccines_vaccine_id_seq    SEQUENCE     �   CREATE SEQUENCE public.vaccines_vaccine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.vaccines_vaccine_id_seq;
       public          postgres    false    225            C           0    0    vaccines_vaccine_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.vaccines_vaccine_id_seq OWNED BY public.vaccines.vaccine_id;
          public          postgres    false    224            ~           2604    17144    animals animal_id    DEFAULT     v   ALTER TABLE ONLY public.animals ALTER COLUMN animal_id SET DEFAULT nextval('public.animals_animal_id_seq'::regclass);
 @   ALTER TABLE public.animals ALTER COLUMN animal_id DROP DEFAULT;
       public          postgres    false    214    215    215                       2604    17154    appointments appointment_id    DEFAULT     �   ALTER TABLE ONLY public.appointments ALTER COLUMN appointment_id SET DEFAULT nextval('public.appointments_appointment_id_seq'::regclass);
 J   ALTER TABLE public.appointments ALTER COLUMN appointment_id DROP DEFAULT;
       public          postgres    false    217    216    217            �           2604    17161     availabledates available_date_id    DEFAULT     �   ALTER TABLE ONLY public.availabledates ALTER COLUMN available_date_id SET DEFAULT nextval('public.availabledates_available_date_id_seq'::regclass);
 O   ALTER TABLE public.availabledates ALTER COLUMN available_date_id DROP DEFAULT;
       public          postgres    false    218    219    219            �           2604    17168    customers customer_id    DEFAULT     ~   ALTER TABLE ONLY public.customers ALTER COLUMN customer_id SET DEFAULT nextval('public.customers_customer_id_seq'::regclass);
 D   ALTER TABLE public.customers ALTER COLUMN customer_id DROP DEFAULT;
       public          postgres    false    221    220    221            �           2604    17177    doctors doctor_id    DEFAULT     v   ALTER TABLE ONLY public.doctors ALTER COLUMN doctor_id SET DEFAULT nextval('public.doctors_doctor_id_seq'::regclass);
 @   ALTER TABLE public.doctors ALTER COLUMN doctor_id DROP DEFAULT;
       public          postgres    false    222    223    223            �           2604    17186    vaccines vaccine_id    DEFAULT     z   ALTER TABLE ONLY public.vaccines ALTER COLUMN vaccine_id SET DEFAULT nextval('public.vaccines_vaccine_id_seq'::regclass);
 B   ALTER TABLE public.vaccines ALTER COLUMN vaccine_id DROP DEFAULT;
       public          postgres    false    225    224    225            -          0    17141    animals 
   TABLE DATA           �   COPY public.animals (animal_id, breed, animal_colour, animal_date_of_birth, animal_gender, animal_name, animal_species, customer_id) FROM stdin;
    public          postgres    false    215   aE       /          0    17151    appointments 
   TABLE DATA           v   COPY public.appointments (appointment_id, appointment_date, appointment_animal_id, appointment_doctor_id) FROM stdin;
    public          postgres    false    217   0G       1          0    17158    availabledates 
   TABLE DATA           e   COPY public.availabledates (available_date_id, available_date, available_date_doctor_id) FROM stdin;
    public          postgres    false    219   �G       3          0    17165 	   customers 
   TABLE DATA              COPY public.customers (customer_id, customer_address, customer_city, customer_mail, customer_name, customer_phone) FROM stdin;
    public          postgres    false    221   4H       5          0    17174    doctors 
   TABLE DATA           q   COPY public.doctors (doctor_id, doctor_address, doctor_city, doctor_mail, doctor_name, doctor_phone) FROM stdin;
    public          postgres    false    223   RI       7          0    17183    vaccines 
   TABLE DATA           �   COPY public.vaccines (vaccine_id, vaccine_code, vaccine_name, vaccine_protection_finish_date, vaccine_protection_start_date, animal_id) FROM stdin;
    public          postgres    false    225   wJ       D           0    0    animals_animal_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.animals_animal_id_seq', 18, true);
          public          postgres    false    214            E           0    0    appointments_appointment_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.appointments_appointment_id_seq', 15, true);
          public          postgres    false    216            F           0    0 $   availabledates_available_date_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.availabledates_available_date_id_seq', 18, true);
          public          postgres    false    218            G           0    0    customers_customer_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.customers_customer_id_seq', 7, true);
          public          postgres    false    220            H           0    0    doctors_doctor_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.doctors_doctor_id_seq', 9, true);
          public          postgres    false    222            I           0    0    vaccines_vaccine_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.vaccines_vaccine_id_seq', 10, true);
          public          postgres    false    224            �           2606    17149    animals animals_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (animal_id);
 >   ALTER TABLE ONLY public.animals DROP CONSTRAINT animals_pkey;
       public            postgres    false    215            �           2606    17156    appointments appointments_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (appointment_id);
 H   ALTER TABLE ONLY public.appointments DROP CONSTRAINT appointments_pkey;
       public            postgres    false    217            �           2606    17163 "   availabledates availabledates_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.availabledates
    ADD CONSTRAINT availabledates_pkey PRIMARY KEY (available_date_id);
 L   ALTER TABLE ONLY public.availabledates DROP CONSTRAINT availabledates_pkey;
       public            postgres    false    219            �           2606    17172    customers customers_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customer_id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    221            �           2606    17181    doctors doctors_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (doctor_id);
 >   ALTER TABLE ONLY public.doctors DROP CONSTRAINT doctors_pkey;
       public            postgres    false    223            �           2606    17192 &   customers uk_5vhox5jsqitujs1k7bcsb2rj8 
   CONSTRAINT     j   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT uk_5vhox5jsqitujs1k7bcsb2rj8 UNIQUE (customer_mail);
 P   ALTER TABLE ONLY public.customers DROP CONSTRAINT uk_5vhox5jsqitujs1k7bcsb2rj8;
       public            postgres    false    221            �           2606    17198 $   doctors uk_7s4l7559eox2hor73b3qp3vq2 
   CONSTRAINT     g   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT uk_7s4l7559eox2hor73b3qp3vq2 UNIQUE (doctor_phone);
 N   ALTER TABLE ONLY public.doctors DROP CONSTRAINT uk_7s4l7559eox2hor73b3qp3vq2;
       public            postgres    false    223            �           2606    17196 $   doctors uk_amsyrdrr2f0d48ciy29o9hvjm 
   CONSTRAINT     f   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT uk_amsyrdrr2f0d48ciy29o9hvjm UNIQUE (doctor_mail);
 N   ALTER TABLE ONLY public.doctors DROP CONSTRAINT uk_amsyrdrr2f0d48ciy29o9hvjm;
       public            postgres    false    223            �           2606    17194 &   customers uk_bn3wq4vhuqco545y52xp96gqd 
   CONSTRAINT     k   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT uk_bn3wq4vhuqco545y52xp96gqd UNIQUE (customer_phone);
 P   ALTER TABLE ONLY public.customers DROP CONSTRAINT uk_bn3wq4vhuqco545y52xp96gqd;
       public            postgres    false    221            �           2606    17190    vaccines vaccines_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.vaccines
    ADD CONSTRAINT vaccines_pkey PRIMARY KEY (vaccine_id);
 @   ALTER TABLE ONLY public.vaccines DROP CONSTRAINT vaccines_pkey;
       public            postgres    false    225            �           2606    17209 '   appointments fk27d0xcbajwaeeun2doojsae4    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fk27d0xcbajwaeeun2doojsae4 FOREIGN KEY (appointment_doctor_id) REFERENCES public.doctors(doctor_id);
 Q   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fk27d0xcbajwaeeun2doojsae4;
       public          postgres    false    217    3218    223            �           2606    17214 *   availabledates fka3446oih0vc95hctragqjwp6y    FK CONSTRAINT     �   ALTER TABLE ONLY public.availabledates
    ADD CONSTRAINT fka3446oih0vc95hctragqjwp6y FOREIGN KEY (available_date_doctor_id) REFERENCES public.doctors(doctor_id);
 T   ALTER TABLE ONLY public.availabledates DROP CONSTRAINT fka3446oih0vc95hctragqjwp6y;
       public          postgres    false    223    219    3218            �           2606    17199 #   animals fkb36lt3kj4mrbdx5btxmp4j60n    FK CONSTRAINT     �   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);
 M   ALTER TABLE ONLY public.animals DROP CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n;
       public          postgres    false    221    215    3212            �           2606    17219 $   vaccines fkeasdy15b2kp5j4k13x2dfudqs    FK CONSTRAINT     �   ALTER TABLE ONLY public.vaccines
    ADD CONSTRAINT fkeasdy15b2kp5j4k13x2dfudqs FOREIGN KEY (animal_id) REFERENCES public.animals(animal_id);
 N   ALTER TABLE ONLY public.vaccines DROP CONSTRAINT fkeasdy15b2kp5j4k13x2dfudqs;
       public          postgres    false    215    3206    225            �           2606    17204 (   appointments fko4t945rb708af9ry6syof7bwt    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fko4t945rb708af9ry6syof7bwt FOREIGN KEY (appointment_animal_id) REFERENCES public.animals(animal_id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fko4t945rb708af9ry6syof7bwt;
       public          postgres    false    215    217    3206            -   �  x�U�An�0E��S�,DJ��e� ^8F�8��X�(Ҡ���Y
t�u6���{ud��h%����
X9S�M���_��V��7���<�qQ@������[kw�$��Ճj��A�g3�f\������6�Xi��I����������iP.��>���s(����N���]����ׁR�(_N{�'L�
�Q�p�u�Cilc�B.d@-k�0�26�����s֪��h:X�s)j�۝��9��1�;\�<]�,�v�f���-���ɸ|�w���ylW�a���|u��(k�m8D�e<ģҧu����з��,����G�/�}��:�ϛ�yE6���F��v$,)�d{TV��Ϧ{����r��J��+�����5�j���br�O�9\/K�8�K:3F��U�	_z��}�."x�Ll��jɴ�Ӵri|$~�s�^FX�W�pe}������c      /   �   x�U���0Ϧ
p������e%��e4�AQ�V�W(EH_DdQcه��<dTC1'+��;~�v:���������F@z�hQ�(�u0zd,r5,��ᨥ53C6���!+C6}/"���7K      1   c   x�M��� E�o�����K��������5���zӤ��q��q��6W��)��E�u����#ݫ;�U�i�4Y�����Xgu�~f� N�&�      3     x�UϱN�0���~G4���D
�!u���C��$U�V8/��`d�;[�{q��l������q���U[a%������3Զr�#2��[��z�(*�8��縴��Vq�*��s��x��7U����0�P4v5�k�Fz��ː�'���Z�w-RX��������,�Ů��m��KV�-�x8_��L�v%4�pm/��	wۢ�1t��;L�"�{=�9� �Z��Ld���tn�Ca�a���7��r��d�k�K[�x������}      5     x�e�MN�0���>A�8qӰ"
��+�Ij��G�-�\���h�D+iV�oF/���{2��M��>uu@�����5lK� ����JU���h�$}��j?'�X��0ۦ����G����q��4�(�]��nIwB�J��x�(��\�VX���6;�,toN��PaY�^�Ų��{��NBAv��
����0�TJ����)c�$�V��������|I�s�������Ņ��R�A5"��i��ڬ�;��{y���&%�!�§��      7   �   x�u��j�0E�3_�p�H�c/SL��.��n&�@~ٔ|~m�Ħ��.�;�������N�͉��u<���tZEe�H�a�R�N^h���0J{�vo�a��@��C����s��a��#�PK#�LG'��Yj�bY1"뛉�2z'���P���t�� ���o ���!/�q9�=׽�U�tW�9F��1U��(~pܭ�@��p���d�	-d��	./r����'��@ߟ�v�i�     