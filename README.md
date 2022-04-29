# IF2210 Tugas Besar 2
Dibuat oleh Safiq Faray (13519145)

## Deskripsi Program
Game kartu turn based untuk 2 pemain. Pemain bermain secara bergantian pada 1 layar yang sama. 
Tujuan dari game ini adalah menghabiskan health points (HP) musuh. 
HP dapat berkurang apabila terkena serangan dari kartu karakter yang diletakkan di board. Dibuat dengan Java

## Struktur Kode
### Class Diagram
![class diagram](https://media.discordapp.net/attachments/943436922615889920/969404444171206696/diagram_oop25.png?width=1134&height=670)
### Struktur files
```sh
├───gradle
│   └───wrapper
└───src # main code here
    └───main
        ├───java
        │   └───com
        │       └───aetherwars # terdapat file AetherWars.java yang berfungsi sebagai program utama (mengandung method static main)
        │           ├───model # tempat dikumpulkannya class-class yang bekerja sebagai objek nantinya
        │           └───util # terdapat CSVReader, untuk membaca file csv
        └───resources # assets
            └───com
                └───aetherwars
                    └───card
                        ├───asciiart # berisi files asciiart yang digunakan dalam program utama
                        ├───data # berisi file csv charactercard, spells, dan deck
                        └───image # tidak digunakan
                            ├───character
                            └───spell
                                ├───morph
                                ├───potion
                                └───swap
```
## Requirements
- Java 8
- Intellij IDEA

## How To Run
Masukkan command `./gradlew run` pada folder ini.

## Screenshots
![screenshot1](https://media.discordapp.net/attachments/943436922615889920/969413845850411018/unknown.png?width=1235&height=670)
![screenshot2](https://media.discordapp.net/attachments/943436922615889920/969413987966013511/unknown.png?width=1440&height=385)
![screenshot3](https://media.discordapp.net/attachments/943436922615889920/969414160028950579/unknown.png?width=1440&height=250)
![screenshot4](https://media.discordapp.net/attachments/943436922615889920/969414263833784440/unknown.png?width=1440&height=593)


