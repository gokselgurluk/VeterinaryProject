


![img.png](img/img.png)


# Veteriner Yönetim Sistemi 
- Veteriner Yönetim Sistemi, veteriner kliniklerinin günlük işlerini düzenlemek ve yönetmek amacıyla oluşturulmuş bir REST API'dir. Bu API ile veteriner çalışanının veteriner doktorları, müşterileri, hayvanları ve aşılarını, randevuları yönetmesi sağlanır.

# Veterinary Management System
- Veterinary Management System is a REST API created to organize and manage the daily affairs of veterinary clinics. With this API, the veterinary worker is enabled to manage veterinarians, customers, animals and their vaccinations, and appointments.

# Kullanılan Teknolojiler / Used Technologies
- Java                     
- Spring Boot 
- PostgresSQL
- Swagger

# Özellikler 
- Veteriner Doktorları Yönetimi: Veteriner doktorları ekleyebilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Müşteri Yönetimi: Müşterileri kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Hayvan Yönetimi: Hayvanları sisteme kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Aşı Yönetimi: Hayvanlara uygulanan aşıları kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Randevu Yönetimi: Veteriner doktorları için randevular oluşturabilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Çeşitli filtreleme yetenekleri

# Features
- Veterinarians Management: Ability to add, update, view and delete veterinarians.
- Customer Management: Ability to register customers, update their information, list and delete them.
- Animal Management: Ability to register animals in the system, update their information, list and delete them.
- Vaccine Management: Ability to record, update, list and delete vaccinations applied to animals.
- Appointment Management: Ability to create, update, view and delete appointments for veterinarians.
- Various filtering capabilities

# UML Diyagram / UML Diagram
![img.png](uml.png)



# API Kullanımı / API Usage

Aşağıda, API'nin sunduğu temel endpoint'lerin bir listesi bulunmaktadır:

Below is a list of the main endpoints the API offers:



| Endpoint                                            | HTTP Metodu | Açıklama                                                                              |
|-----------------------------------------------------|:------------|---------------------------------------------------------------------------------------|
| **customers**                                       |             |                                                                                       |
| `/api/v1/customers/filter/{id}`                     | GET         | Belirtilen ID'ye sahip hayvan sahibini getirir                                        |
| `/api/v1/customers/update/{id}`                     | PUT         | Belirtilen ID'ye sahip hayvan sahibini günceller                                      |
| `/api/v1/customers/{id}`                            | DELETE      | Belirtilen ID'ye sahip hayvan sahibini siler                                          |
| `/api/v1/customers/List`                            | GET         | Tüm hayvan sahiplerini getirir                                                        |
| `/api/v1/customers/created`                         | POST        | Hayvan sahibi ekler                                                                   |
| `/api/v1/customers/filter/Name`                     | GET         | İsme gore hayvan sahiplerini getirir                                                  |
|                                                     |             |                                                                                       |
| **animals**                                         |             |                                                                                       |
| `/api/v1/animals/{id}`                              | GET         | Belirtilen ID'ye sahip hayvanı getirir                                                |
| `/api/v1/animals/update/{id}`                       | PUT         | Belirtilen ID'ye sahip hayvanı günceller                                              |
| `/api/v1/animals/{id}`                              | DELETE      | Belirtilen ID'ye sahip hayvanı siler                                                  |
| `/api/v1/animals/All/List`                          | GET         | Tüm hayvanları getirir                                                                |
| `/api/v1/animals/vaccines/List/{id}`                | GET         | Hayvana ait aşı kayıtlarını listeler                                                  |
| `/api/v1/animals/created`                           | POST        | Hayvan ekler                                                                          |
| `/api/v1/animals/filter/animal/{name}`              | GET         | İsme göre hayvanları filtreler                                                        |
| `/api/v1/animals/filterCustomerName/{customerName}` | GET         | Hayvan sahiplerine göre hayvanları filtreler                                          |
|                                                     |                                    |                                                                                       |                                                                                    |
| **vaccines**                                        |             |                                                                                       |
| `/api/v1/vaccines/{id}`                             | GET         | Belirtilen aşı ID'ye sahip aşıyı getirir                                              |
| `/api/v1/vaccines/update/{id}`                      | PUT         | Belirtilen ID'ye sahip aşıyı günceller                                                |
| `/api/v1/vaccines/{id}`                             | DELETE      | Belirtilen ID'ye sahip aşıyı siler                                                    |
| `/api/v1/vaccines/List/{animalId}`                  | GET         | Belirtilen hayvan ID'ye sahip aşıları getırır                                         |
| `/api/v1/vaccines/created`                          | POST        | Aşı ekler                                                                             |
| `/api/v1/vaccines/dates-range`                      | GET         | Girilen tarih araligina gore aşı kayıtlarını getirir                                  |
| `/api/v1/vaccines/animal/filter/vaccineFinishDate`  | GET         | Girilen tarih araligina gore aşı koruyculugu bitiş tarihine uygun kayıtlarını getirir |
| `/api/v1/vaccines/animal/filter/vaccineFinishDate`  | GET         | Girilen tarih araligina ve aniaml id ye gore uygun kayıtları  getirir                 |
| `/api/v1/vaccines/All/List`                         | GET         | Tüm aşı kayıtlarını getirir                                                           |
|                                                     |             |                                                                                       |
| **doctors**                                         |             |                                                                                       |
| `/api/v1/doctors/{id}`                              | GET         | Belirtilen ID'ye sahip doktoru getirir                                                |
| `/api/v1/doctors/update/{id}`                       | PUT         | Belirtilen ID'ye sahip doktoru günceller                                              |
| `/api/v1/doctors/filter/doctor/{name}`              | GET         | Belirtilen Doctor ismine sahip doktoru getirir                                        |
| `/api/v1/doctors/{id}`                              | DELETE      | Belirtilen ID'ye sahip doktoru siler                                                  |
| `/api/v1/doctors/All/List`                          | GET         | Tum doktorlari getirir                                                                |
| `/api/v1/doctors/created`                           | POST        | Doktor ekler                                                                          |
|                                                     |             |                                                                                       |
| **available_dates**                                 |             |                                                                                       |
| `/api/v1/available_dates/{id}`                      | GET         | Belirtilen ID'ye sahip müsait günü getirir                                            |
| `/api/v1/available_dates/update/{id}`               | PUT         | Belirtilen ID'ye sahip müsait günü günceller                                          |
| `/api/v1/available_dates/{id}`                      | DELETE      | Belirtilen ID'ye sahip müsait günü siler                                              |
| `/api/v1/available_dates/All/List`                  | GET         | Tüm müsait günü getirir                                                               |
| `/api/v1/available_dates/created`                   | POST        | Müsait gün ekler                                                                      |
| `/api/v1/available_dates/filterDoctorId/{id}`       | GET         | Belirtilen doctor ID'ye sahip müsait günü getirir                                     |
| **appointments**                                    |             |                                                                                       |
| `/api/v1/appointments/{id}`                         | GET         | Belirtilen ID'ye sahip randevuyu getirir                                              |
| `/api/v1/appointments/update/{id}`                  | PUT         | Belirtilen ID'ye sahip randevuyu günceller                                            |
| `/api/v1/appointments/filter/doctor/{doctorId}`     | GET         | Belirtilen ID'ye sahip randevuyu getirir                                              |
| `/api/v1/appointments/filter/animal/{animalId}`     | GET         | Belirtilen ID'ye sahip randevuyu getirir                                              |
| `/api/v1/appointments/{id}`                         | DELETE      | Belirtilen ID'ye sahip randevuyu siler                                                |
| `/api/v1/appointments/All/List`                     | GET         | Tüm randevulari getirir                                                               |
| `/api/v1/appointments/created`                      | POST        | Randevu ekler                                                                         |
| `/api/v1/appointments/filter/dateANDdoctor`         | GET         | Kullanıcı tarafından girilen tarih aralığına ve doktora göre randevuları filtreler    |
| `/api/v1/appointments/filter/dateANDanimal`         | GET         | Kullanıcı tarafından girilen tarih aralığına ve hayvana göre randevuları filtreler    |
