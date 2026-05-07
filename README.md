# 🎮 **Petak Umpet Teleport - Minecraft Plugin**

**Version**: 1.0-release  
**Jakarta**: May 8, 2026  
**Status**: ✅ Production Ready

---

## 📋 **Table of Contents**

1. [Overview](#-overview)
2. [Features](#-features)
3. [Installation](#-installation)
4. [Quick Start](#-quick-start)
5. [Commands](#-commands)
6. [Game Mechanics](#-game-mechanics)
7. [Teleport Types](#-teleport-types)
8. [Scoring System](#-scoring-system)
9. [Combat Rules](#-combat-rules)
10. [Game Flow Diagram](#-game-flow-diagram)
11. [Multi-Round Tournament](#-multi-round-tournament)
12. [Troubleshooting](#-troubleshooting)

---

## 🎯 **Overview**

**Petak Umpet Teleport** adalah Minecraft plugin hide-and-seek yang inovatif dengan mekanik **teleportasi acak setiap menit** untuk meningkatkan gameplay yang chaotic dan exciting!

### **Core Concept**
- **1 Hunter** vs **Multiple Hiders** dalam satu round
- **Sistem Poin** berbasis urutan kematian dan kills
- **5 Variasi Teleport** yang random setiap menit untuk shake-up gameplay
- **Ghost System** - Hiders yang mati jadi ghost bisa membunuh hiders lain untuk poin
- **Multi-Round** - Siapa yang sudah jadi hunter tidak bisa jadi hunter lagi sampai reset
- **Dynamic Capacity** - Support 6-12+ players

---

## ✨ **Features**

### **Core Gameplay**
✅ Hide & Seek mode dengan 1 Hunter vs Multiple Hiders  
✅ **1 menit** hide phase + **5 menit** hunt phase per round  
✅ 5 variasi teleport yang random setiap menit  
✅ Ghost mechanic - hiders mati jadi bisa membunuh juga  
✅ Multi-round tournament support  
✅ Dynamic scoring system  

### **Advanced Features**
✅ Nametag hiding (players tidak bisa lihat satu sama lain)  
✅ Strength effect untuk Hunter & Ghost (1-hit kill)  
✅ Hunter gacha animation  
✅ Real-time timer display  
✅ Automatic round detection  
✅ Leaderboard system  
✅ Score accumulation across rounds  

### **Teleport Mechanics**
✅ **Type 0**: Semua player shuffle posisi (Chaotic!)  
✅ **Type 1**: Hider alive only shuffle, Hunter tetap  
✅ **Type 2**: 2/3 random players shuffle (Semi-chaotic)  
✅ **Type 3**: Fake teleport (visual only, prank!)  
✅ **Type 4**: Hiders cycle pattern (predictable)  

---

## 🔧 **Installation**

### **Requirements**
- **Minecraft Server**: Paper 1.21.11 atau lebih baru
- **Java**: JDK 21 atau lebih baru
- **Maven**: Untuk build dari source

### **Step 1: Build from Source**

```bash
# Clone atau download project
cd petakUmpetTeleport

# Build menggunakan Maven
mvn clean package

# JAR akan generate di folder target/
# File: petakUmpetTeleport-1.0-release.jar
```

### **Step 2: Install Plugin**

```bash
# Copy JAR ke server plugins folder
cp target/petakUmpetTeleport-1.0-release.jar /path/to/server/plugins/

# Restart server
# Plugin akan auto-create konfigurasi jika diperlukan
```

### **Step 3: Verify Installation**

```bash
# Di dalam game, ketik:
/tpinfo

# Jika muncul info teleport types, plugin sudah berhasil!
```

---

## 🚀 **Quick Start**

### **5 Menit Setup**

```bash
1. /regis Player1
2. /regis Player2
3. /regis Player3
4. /regis Player4
5. /regis Player5
6. /regis Player6
   ↓
7. /gacha              # Pilih hunter secara random
   ↓
8. /start              # Game dimulai!
   • 60 detik: Player bersembunyi
   • 300 detik: Hunter berburu
   ↓
9. Player mati satu demi satu
   ↓
10. /listscore         # Cek score
    ↓
11. /nextround         # Siap round baru, atau
    /endgame           # Tutup game
```

---

## 📖 **Commands**

### **Admin Commands**

#### **1. /regis <PlayerName>**
Mendaftarkan player untuk tournament.

```bash
/regis Steve
/regis Alex
/regis Notch
```

**Requirements**: OP status  
**Status**: Tidak bisa regis saat game running  
**Max Players**: Unlimited (tested 6-12+)

---

#### **2. /unregis <PlayerName>**
Membatalkan registrasi player (jika disconnect atau emergency).

```bash
/unregis Steve
```

**Use Case**: Player DC/mati listrik, perlu dihapus dari daftar  
**Status**: Tidak bisa saat game running

---

#### **3. /listplayer**
Menampilkan daftar semua player yang terdaftar.

```bash
/listplayer

# Output:
# ===== DAFTAR PESERTA PETAK UMPET =====
# - Steve
# - Alex
# - Notch
# - Herobrine
# - Ender
# - Skeleton
```

---

#### **4. /gacha**
Memilih hunter secara random dari player yang belum pernah jadi hunter di tournament ini.

```bash
/gacha

# Output dengan animasi:
# Title: "Memilih Hunter..."
# 20 ticks: Menampilkan random names
# Kemudian: "STEVE terpilih menjadi HUNTER!"
```

**Requirements**: 
- Min 1 player registered
- Ada player yang belum pernah jadi hunter
- Game tidak sedang running

---

#### **5. /start**
Memulai game dengan:
- 60 detik hide phase (hunter freeze + blind)
- 300 detik hunt phase (hunter berburu)

```bash
/start

# Flow:
# - Action bar: "KAMU SEDANG DI-FREEZE! 60s" (untuk hunter)
# - Action bar: "Waktu Ngumpet: 60s" (untuk hiders)
# - Countdown 5-1 second visual
# - Hunter dilepaskan setelah 60s
```

**Requirements**: Hunter sudah dipilih via /gacha  
**Auto Triggers**: 
- 5x teleport per round (setiap menit di 5:00, 4:00, 3:00, 2:00, 1:00)
- Automatic round detection saat semua hiders dead

---

#### **6. /nextround**
Mempersiapkan round berikutnya **DENGAN TETAP MENJAGA SCORES**.

```bash
/nextround

# Reset untuk round baru:
# - Hunter: null
# - deadCount: 0
# - eliminatedPlayers: cleared
# - ghostPlayers: cleared
# 
# TETAP:
# - Scores dari semua rounds
# - pastHunters history (untuk filter gacha)
# - Participants list
```

**When to Use**: Setelah round selesai, mau main round baru  
**Next Steps**: /gacha → /start

---

#### **7. /endgame**
Mengakhiri tournament dan menampilkan **FINAL LEADERBOARD**.

```bash
/endgame

# Output:
# ====== LEADERBOARD LAPER GANG ======
# Steve: 35 Poin 🥇
# Alex: 28 Poin 🥈
# Notch: 18 Poin 🥉
# Herobrine: 5 Poin
# Ender: -2 Poin
# Skeleton: -5 Poin
# ====================================
```

**Result**: Game selesai, tidak bisa /nextround lagi  
**When to Use**: Tournament dinilai selesai

---

#### **8. /listscore**
Menampilkan leaderboard saat ini (cumulative scores dari semua rounds).

```bash
/listscore

# Sama dengan /endgame output
# Bisa dipanggil kapan saja untuk cek score terkini
```

---

#### **9. /resetgame**
**HARD RESET** semua data game - scores, hunter history, everything!

```bash
/resetgame

# Reset:
# ❌ Scores: 0 untuk semua
# ❌ pastHunters: kosong
# ❌ currentHunter: null
# ❌ All game state
#
# TETAP:
# ✅ Participants list (players tetap registered)
```

**⚠️ WARNING**: Ini irreversible! Final leaderboard akan dihapus!  
**When to Use**: Mau start tournament baru dari 0

---

#### **10. /tpinfo**
Menampilkan informasi lengkap 5 tipe teleport.

```bash
/tpinfo

# Output:
# ========== TIPE-TIPE TELEPORT ==========
# [0] Swap Semua Player
#     → Semua pemain shuffle ke posisi pemain lain
# [1] Random Swap Hiders Only
#     → Hanya hiders shuffle, Hunter tetap
# [2] Mix Swap (Dynamic 2/3)
#     → 2/3 dari semua player shuffle
# [3] Fake Swap (No Teleport)
#     → Visual effect only, tidak ada teleport
# [4] Fixed Swap Hiders Pattern
#     → Hiders cycle shuffle (predictable)
# =========================================
```

---

## 🎮 **Game Mechanics**

### **Game Phases**

#### **Phase 1: Registration**
- Admin regis semua players dengan `/regis`
- Bisa check dengan `/listplayer`
- Bisa unregis dengan `/unregis` jika ada yang DC

#### **Phase 2: Hunter Selection**
- Admin jalankan `/gacha` untuk random select hunter
- Hunter dipilih dari player yang belum pernah jadi hunter
- Hunter announcement dengan title screen

#### **Phase 3: Hide Phase (60 sekon)**
- Hunter: **FROZEN** (Blindness + Slowness)
- Hiders: Berlari bersembunyi
- Countdown: 5-1 detik warning
- After 60s: Hunter dilepaskan dengan **Netherite Sword + STRENGTH II**

#### **Phase 4: Hunt Phase (300 sekon)**
- Hunter: Berburu hiders
- Hiders: Bersembunyi + strategize
- Teleport mechanics: Setiap menit (5:00, 4:00, 3:00, 2:00, 1:00)
- **5 teleport types** dipilih random

#### **Phase 5: Round End**
- Saat semua hiders dead → Round selesai
- Scores di-calculate dan ditampilkan
- Admin choose: `/nextround` atau `/endgame`

---

### **Player States**

#### **1. Hider Alive** 
- Status: Sehat, masih bersembunyi
- Abilities: 
  - ❌ Tidak bisa bunuh hunter
  - ❌ Tidak bisa bunuh ghost
  - ✅ Bisa berkomunikasi dengan hiders lain
- Effects: None

#### **2. Ghost**
- Status: Mati, tapi bisa membunuh hiders
- How Become: Terbunuh oleh hunter
- Abilities:
  - ✅ Bisa membunuh hiders alive (dapat +1)
  - ✅ Membantu hunter secara teamwork (tapi tidak langsung)
  - ❌ Tidak bisa bunuh hunter
  - ❌ Tidak bisa bunuh ghost lain
- Effects: **STRENGTH II** (1-hit kill hiders)
- Score: 
  - **Initial**: -(kematian urutan)
  - **Bonus**: +1 untuk setiap hider yang dibunuh

#### **3. Hunter**
- Status: Pemburu utama
- Abilities:
  - ✅ Bisa membunuh hiders (dapat +1)
  - ✅ Bisa dipengaruhi teleport
  - ❌ Tidak bisa bunuh ghost
- Effects: 
  - **STRENGTH II** (1-hit kill)
  - **NETHERITE SWORD** di inventory
- Score: +1 untuk setiap hider yang dibunuh

---

## 🌪️ **Teleport Types**

### **TYPE 0: Swap Semua Player** ⚡
**Chaos Level**: 🔴🔴🔴 (MAX)

```
BEFORE TELEPORT:
Hunter di position (50, 100)
Hider1 di position (60, 100)
Hider2 di position (70, 100)
Ghost1 di position (80, 100)
Ghost2 di position (90, 100)

AFTER TELEPORT:
Hunter → position Hider2 (70, 100)
Hider1 → position Ghost2 (90, 100)
Hider2 → position Ghost1 (80, 100)
Ghost1 → position Hunter (50, 100)
Ghost2 → position Hider1 (60, 100)

RESULT: SEMUA berpindah, unpredictable chaos!
```

**Broadcast**: "🌪️ SEMUA PEMAIN BERTUKAR POSISI!"

---

### **TYPE 1: Random Swap Hiders Only** 🎯
**Chaos Level**: 🟡 (MEDIUM)

```
BEFORE TELEPORT:
Hunter di position (50, 100) ← TETAP!
Hider1 di position (60, 100)
Hider2 di position (70, 100)
Ghost1 di position (80, 100) ← TETAP!

AFTER TELEPORT:
Hunter → (50, 100) ← SAMA
Hider1 → (70, 100) [Hider2's original]
Hider2 → (60, 100) [Hider1's original]
Ghost1 → (80, 100) ← SAMA

RESULT: Hiders shuffle, Hunter & Ghost tetap
```

**Broadcast**: "Hiders bertukar posisi satu sama lain! Ghost & Hunter tetap ditempat."

---

### **TYPE 2: Mix Swap (Dynamic 2/3)** 🎲
**Chaos Level**: 🟠 (MEDIUM-HIGH)

```
TOTAL: 6 Player (1 Hunter + 2 Ghost + 3 Hider)
SWAP: 2/3 dari 6 = 4 player
STAY: 1/3 dari 6 = 2 player

POSSIBILITY 1:
├─ 4 player swap: [Hunter, Hider1, Hider2, Ghost1]
└─ 2 player stay: [Hider3, Ghost2]

POSSIBILITY 2:
├─ 4 player swap: [Hider1, Hider2, Hider3, Ghost1]
└─ 2 player stay: [Hunter, Ghost2]

RESULT: Random mix, bisa siapa saja yang swap!
```

**Broadcast**: "⚡ Swap 4 player, 2 stay: [names]"

**Formula**: 
```
swapCount = Math.max(2, allPlayers.size() * 2 / 3)
swapCount = Math.min(swapCount, allPlayers.size() - 1)

Examples:
- 6 player → swap 4, stay 2
- 9 player → swap 6, stay 3
- 12 player → swap 8, stay 4
```

---

### **TYPE 3: Fake Swap** 🎭
**Chaos Level**: 🟢 (NONE)

```
EFFECT:
1. Semua player: Teleport sound effect
2. Semua player: Nausea potion effect (20 ticks)
3. Broadcast: "Telah dimulai..."
4. After 2 sekon: "FALSE ALARM! ❌ Tidak ada teleport kali ini!"

RESULT: Posisi semua tetap, cuma visual/audio prank!
```

**Broadcast**: "Telah dimulai... FALSE ALARM! ❌ Tidak ada teleport kali ini!"

---

### **TYPE 4: Fixed Swap Hiders Pattern** 🔄
**Chaos Level**: 🟡 (LOW-MEDIUM - Predictable)

```
BEFORE TELEPORT:
Hider1 di position A
Hider2 di position B
Hider3 di position C
Hunter di position D ← TETAP!

ROTATION:
Hider1 → position B (Hider2's original)
Hider2 → position C (Hider3's original)
Hider3 → position A (Hider1's original)
Hunter → position D ← TETAP!

PATTERN: A→B→C→A (Cycle)
RESULT: Predictable, tapi tetap bisa confuse!
```

**Broadcast**: "Hiders bertukar posisi (cycle)! Ghost & Hunter tetap ditempat."

---

## 💯 **Scoring System**

### **Death Penalty** (Saat Hider Mati)

| Order | Formula | Example (6 hiders) |
|-------|---------|-------------------|
| 1st to die | -(totalHiders - 0) | -5 |
| 2nd to die | -(totalHiders - 1) | -4 |
| 3rd to die | -(totalHiders - 2) | -3 |
| 4th to die | -(totalHiders - 3) | -2 |
| 5th to die | -(totalHiders - 4) | -1 |
| 6th to die | -(totalHiders - 5) | -1 (minimum) |

**Formula**: `penalty = -(hiderCount - (deathOrder - 1))`

**Logic**: 
- Mati lebih cepat = penalty lebih besar
- Mati paling akhir = penalty paling kecil (-1)

---

### **Kill Bonus**

| Who Killed | Bonus |
|-----------|-------|
| Hunter | +1 per hider |
| Ghost | +1 per hider (bisa cumulative) |
| Hider Alive | 0 (tidak bisa membunuh) |

**Message**: 
- Hunter: "§a+1 Poin Kill!"
- Ghost: "§7[GHOST] §a+1 Poin Kill!"

---

### **Round Example**

```
ROUND 1: 6 Player (1 Hunter + 5 Hider)

Timeline:
├─ 0:00-1:00 → Hide Phase
├─ 1:00 → Hider5 killed by Hunter → Score: -1, Hunter: +1
├─ 1:30 → Hider4 killed by Ghost1 → Score: -2, Ghost1: +1
├─ 2:00 → TELEPORT TYPE 2 (Mix swap)
├─ 2:45 → Hider3 killed by Hunter → Score: -3, Hunter: +1
├─ 3:20 → Hider2 killed by Ghost1 → Score: -4, Ghost1: +1
├─ 4:00 → TELEPORT TYPE 0 (All swap)
├─ 4:30 → Hider1 killed by Ghost2 → Score: -5, Ghost2: +1
└─ 5:00 → ROUND SELESAI

FINAL SCORES (Round 1):
┌──────────────────────────┐
│ Hunter: +3 (3 kills)     │
│ Ghost1: +2 (2 kills)     │
│ Ghost2: +1 (1 kill)      │
│ Hider5: -1               │
│ Hider4: -2               │
│ Hider3: -3               │
│ Hider2: -4               │
│ Hider1: -5               │
└──────────────────────────┘
```

---

## ⚔️ **Combat Rules**

### **Rule 1: Ghost Tidak Bisa Saling Membunuh**
```
if (attacker is GHOST && victim is GHOST) {
    ❌ Attack cancelled
    Message: "Ghost tidak bisa saling membunuh!"
}
```

**Rationale**: Prevent ghost teamkill sesama ghost

---

### **Rule 2: Ghost Tidak Bisa Bunuh Hunter**
```
if (attacker is GHOST && victim is HUNTER) {
    ❌ Attack cancelled
    Message: "Ghost tidak bisa membunuh Hunter!"
}
```

**Rationale**: Hunter protected dari ghost coordination

---

### **Rule 3: Hider Alive Tidak Bisa Bunuh Ghost**
```
if (attacker is HIDER_ALIVE && victim is GHOST) {
    ❌ Attack cancelled
    Message: "Hider tidak bisa membunuh Ghost!"
}
```

**Rationale**: Prevent hider kill ghost (ghost sudah aman)

---

### **Rule 4: Hider Alive Tidak Bisa Bunuh Hunter**
```
if (attacker is HIDER_ALIVE && victim is HUNTER) {
    ❌ Attack cancelled
    Message: "Hider tidak bisa menyakiti Hunter!"
}
```

**Rationale**: Hiders cannot harm hunter (core mechanic)

---

### **Rule 5: Hunter Tidak Bisa Bunuh Ghost**
```
if (attacker is HUNTER && victim is GHOST) {
    ❌ Attack cancelled
    Message: "Ghost sudah aman dari Hunter!"
}
```

**Rationale**: Ghost protected from hunter after death

---

## 📊 **Game Flow Diagram**

```
ADMIN SETUP PHASE
├─ /regis Player1-6
├─ /listplayer (verify)
└─ Ready to start

TOURNAMENT PHASE (Round 1)
├─ /gacha
│  └─ Hunter selected (random from all players)
│
├─ /start
│  ├─ 60 sekon HIDE PHASE
│  │  ├─ Hunter: FROZEN (Blindness + Slowness)
│  │  └─ Hiders: Running to hide
│  │
│  └─ 300 sekon HUNT PHASE
│     ├─ Hunter: Released with Sword + STRENGTH
│     ├─ Teleport triggers: 5:00, 4:00, 3:00, 2:00, 1:00
│     │  └─ Random TYPE (0-4)
│     ├─ Hiders: Getting killed one by one
│     └─ When killed:
│        ├─ Add to eliminatedPlayers
│        ├─ Become GHOST with STRENGTH
│        └─ Can hunt other hiders for bonus +1
│
├─ Round End Detection (all hiders dead)
│  ├─ eliminatedPlayers cleared
│  └─ ghostPlayers cleared
│
├─ /listscore
│  └─ Show scores after round 1
│
├─ Choice:
│  ├─ /nextround → Go to Round 2
│  │  └─ /gacha → /start (repeat)
│  │
│  └─ /endgame → Show FINAL LEADERBOARD
│     └─ Tournament selesai

OPTIONAL:
└─ /resetgame (hard reset untuk tournament baru dari 0)
```

---

## 🏆 **Multi-Round Tournament**

### **3-Round Tournament Example**

```
INITIAL: 6 Players Registered

ROUND 1: /gacha → /start (all players eligible)
├─ Hunter: Player1 (selected)
├─ Hiders: Player2-6
├─ Scores after:
│  ├─ Player1 (Hunter): +3
│  ├─ Player2 (Ghost): +1
│  └─ Player3: -2 (etc)
│
├─ /nextround
│  └─ Reset deadCount, eliminatedPlayers, ghostPlayers
│  └─ Scores TETAP accumulated
│  └─ pastHunters: [Player1]

ROUND 2: /gacha → /start (only Player2-6 eligible)
├─ Hunter: Player4 (selected, Player1 cannot)
├─ Hiders: Player1, Player2, Player3, Player5, Player6
├─ Scores after:
│  ├─ Player7: +2 (cumulative: +3+2=+5)
│  ├─ Player1: -1 (cumulative: 0-1=-1)
│  └─ Player4 (Hunter R2): +2 (cumulative: 0+2=+2)
│
├─ /nextround
│  └─ pastHunters: [Player1, Player4]

ROUND 3: /gacha → /start (only Player2,3,5,6 eligible)
├─ Hunter: Player6 (selected)
├─ Continue...
│
├─ /endgame
│  └─ SHOW FINAL LEADERBOARD (all 3 rounds accumulated)

FINAL LEADERBOARD (Example):
┌─────────────┐
│ Player1: 25 │ 🥇
│ Player4: 20 │ 🥈
│ Player3: 18 │ 🥉
│ Player6: 10 │
│ Player2:  5 │
│ Player5: -3 │
└─────────────┘
```

---

## 🔧 **Troubleshooting**

### **Problem: "Semua peserta sudah pernah jadi Hunter!"**

**Cause**: All players sudah di-gacha sebelumnya di tournament ini  
**Solution**: 
```bash
/resetgame    # Hard reset, clear pastHunters
/gacha        # Try again
```

---

### **Problem: Hunter tidak dapat Sword**

**Cause**: Hunter offline saat freeze phase berakhir  
**Solution**: 
```bash
/endgame
/regis [new_player]
/gacha
/start        # Try again
```

---

### **Problem: Nametags masih terlihat**

**Cause**: Plugin belum fully load atau player join mid-game  
**Solution**: 
```bash
# Restart server
# Plugin akan auto-hide nametags saat enable
```

---

### **Problem: Teleport tidak trigger**

**Cause**: Game loop tidak running atau teleport type 3 (fake)  
**Solution**: 
```bash
# Wait for next minute mark (5:00, 4:00, etc)
# Or check if game is running: /gacha (should fail if running)
```

---

### **Problem: Ghost dapat strength tapi tidak 1-hit**

**Cause**: Strength level tidak correct (ampl 255 invalid)  
**Solution**: 
```bash
# Plugin already fixed: Strength II (ampl 1) = correct 1-hit
# May need plugin update if old version used
```

---

### **Problem: Score tidak accumulated ke round berikutnya**

**Cause**: `/resetgame` was called (hard reset all scores)  
**Solution**: 
```bash
# Use /nextround instead (keeps scores)
# /resetgame = NEW TOURNAMENT from 0
```

---

## 📝 **Configuration**

### **Server Requirements**
- **Minecraft**: 1.21.11+
- **Java**: JDK 21+
- **RAM**: 512MB minimum (per plugin)
- **Plugins**: None (standalone)

### **Supported Features by Version**
- **1.21.11**: ✅ Full support
- **1.20.x**: ⚠️ May work but untested
- **1.19 and below**: ❌ NOT supported

---

## 📚 **Advanced Tips**

### **Tip 1: Teleport Strategy**
- Teleport tidak always good untuk hunter (bisa teleport jauh dari hiders)
- Type 3 (Fake) = Psychological warfare!
- Mix different types per minute untuk unpredictability

### **Tip 2: Ghost Coordination**
- Ghosts cannot communicate after death (no chat)
- But can use sound cues (footsteps)
- Ghost bonus +1 incentivize teamwork

### **Tip 3: Hunter Advantage**
- STRENGTH II = 1-hit kill any hider
- BUT teleport bisa separate dari hiders
- Hiders bisa use scattered positions for advantage

### **Tip 4: Scoring Meta**
- Early deaths = bigger penalties (incentivize survival)
- Ghost kills = bonus (incentivize helping)
- Long game rounds = more consistent scores

### **Tip 5: Multi-Round Balance**
- Later rounds hunters get "advantage" (fewer available)
- Earlier rounds more competitive (all eligible)
- Balance achieved through random gacha

---

## 🎓 **Examples**

### **Example 1: Simple 1-Round Game**

```
/regis Alice
/regis Bob
/regis Charlie
/regis Diana
/regis Eve
/regis Frank

/gacha           # Bob selected as Hunter

/start           # Game begins!
               # 60s hide + 300s hunt
               # Bob kills someone every 30-60s

/endgame         # Show leaderboard
               # Scores displayed
```

**Time**: ~10 menit total

---

### **Example 2: Tournament 3 Rounds**

```
ROUND 1:
/gacha → /start (All eligible for hunter)
/listscore

ROUND 2:
/nextround
/gacha → /start (Selected player cannot be hunter)
/listscore

ROUND 3:
/nextround
/gacha → /start
/endgame

# Show final leaderboard after all 3 rounds
# Scores accumulated from all rounds
```

**Time**: ~40 menit total

---

## 📞 **Support**

### **Issues?**
1. Check Troubleshooting section above
2. Verify server logs (console)
3. Check plugin version (1.0-release expected)

---

## 📄 **License**

**Petak Umpet Teleport** - Custom Minecraft Plugin  
**Status**: Production Ready  
**Tested**: Minecraft 1.21.11 Paper  
**Java**: JDK 21  

---

## 🙏 **Credits**

- **Concept**: Petak Umpet Hide-and-Seek
- **Implementation**: Teleport plugin system
- **Version**: 1.0-release
- **Build Date**: May 8, 2026

---

**Happy Playing! 🎮🌪️**

Enjoy the chaotic teleport-based hide-and-seek experience!


>>>>>>> master
