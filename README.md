# Tietoa sovelluksesta


---

## Käyttäjätunnukset

Seuraavat testitunnukset on luotu valmiiksi sovelluksen testauksen helpottamiseksi:

1. **Admin-tunnus:**
    - **Käyttäjätunnus:** `admin`
    - **Salasana:** `123`
    - **Roolit:** `User, Admin`

2. **Tunnus 1:**
    - **Käyttäjätunnus:** `1`
    - **Salasana:** `1`
    - **Roolit:** `User`

3. **Tunnus 2:**
    - **Käyttäjätunnus:** `2`
    - **Salasana:** `2`
    - **Roolit:** `User`

### Uuden käyttäjän luonti
- Jos luot oman käyttäjätunnuksen, sen roolina on automaattisesti `User`.
- Huomio, että jokaisen uuden käyttäjän tietoja käsitellään kirjautuneen käyttäjän oikeuksilla ja roolilla.

---

## Sovelluksen käyttö

### Mittaustulokset
- Jokaisella käyttäjällä on oma käyttäjäkohtainen tietokokoelma mittaustuloksia.
- Kirjautuneena voit:
    - Lisätä uusia mittaustuloksia **"Measurement"**-välilehdeltä.
    - Tarkastella ja suodattaa niitä **"Results"**-välilehdellä.
    - Kaikki käsittely (lisääminen, muokkaaminen, poistaminen) koskee vain **kirjautuneen käyttäjän omia tuloksia**.

### Selaimen välimuisti
- **Huomio!** Jos luot käyttäjän ja kirjaudut sisään, varmista **kirjautuminen ulos ennen sovelluksen sulkemista**.
    - Selaimen välimuisti voi seuraavalla käynnistyskerralla aiheuttaa ongelmia, jos logout-komento jää suorittamatta.
    - Tällöin käyttöliittymä voi näyttää "Login" ja "Register" -toiminnot väärin, vaikka roolit olisivat edelleen käytettävissä.

---

## Teknisiä huomioita

- **Salasanat ovat salattu algoritmillä**:
    - Konsoliin tulostuu sovelluksen käynnistyksen yhteydessä kirjautuneiden käyttäjien salatun salasanan tiedot ihan vain demostraation puolesta.

- **Tietokanta puuttuu**:
    - Tämä sovellus ei käytä pysyvää tietokantaa.
    - Kaikki käyttäjätiedot ja mittaustulokset ovat aktiivisia vain sovelluksen käynnissäoloaikana.
    - Tietokannan lisäyksellä vältettäisiin ongelmat, jotka liittyvät selaimen välimuistiin ja kirjautumistietoihin.

---

## Ominaisuudet

1. **Käyttäjähallinta:**
    - Mahdollisuus luoda ja hallita käyttäjätilejä.
    - Käyttäjien tiedot ovat tilapäisiä, säilyvät vain sovelluksen päällä ollessa.

2. **Mittaustulokset:**
    - Henkilökohtaiset ja käyttäjäkohtaiset mittaustulokset.
    - Mahdollisuus lisätä, tarkastella, suodattaa, muokata ja poistaa mittauksia.

3. **Roolit ja oikeudet:**
    - Käyttäjän roolit (esim., `User` tai `Admin`) vaikuttavat heidän oikeuksiinsa.

---

