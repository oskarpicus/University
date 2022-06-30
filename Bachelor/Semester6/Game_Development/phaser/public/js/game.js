let gameScene = new Phaser.Scene('Game');
let nrPlayers = 1;
let scoreP1 = 0, scoreP2 = 0;

gameScene.preload = function () {
    this.load.spritesheet('character', 'assets/character/character.png', {
        frameWidth: 48,
        frameHeight: 48
    });
    this.load.spritesheet('mouse', 'assets/mobs/mouse.png', {
        frameWidth: 17,
        frameHeight: 16,
    });
    this.load.image('background', 'assets/tiles/Grass.png');
};

gameScene.create = function () {
    this.speed = 2;
    this.cursors = this.input.keyboard.createCursorKeys();
    this.keyA = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.A);
    this.keyS = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.S);
    this.keyD = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.D);
    this.keyW = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.W);

    let background = this.add.sprite(0, 0, 'background');
    background.setOrigin(0, 0);
    background.setScale(Math.max(window.innerWidth / background.width, window.innerHeight / background.height));

    this.enemies = this.physics.add.group();
    for (let i = 0; i < 5; i++) {
        const x = Phaser.Math.RND.between(0, this.sys.game.config.width);
        const y = Phaser.Math.RND.between(0, this.sys.game.config.height);
        const mouse = this.physics.add.sprite(x, y, 'mouse');
        mouse.setScale(3);
        this.enemies.add(mouse);
    }

    this.character = this.physics.add.sprite(window.innerWidth / 2, window.innerHeight / 2, 'character');
    this.character.setScale(4);
    this.character.width = 10;
    this.character.height = 10;

    this.score = this.add.text(10, 0, `P1: ${scoreP1}`, {
        fontSize: '32px',
    });

    this.physics.add.overlap(this.character, this.enemies, (_, obj2) => {
        obj2.x = Phaser.Math.RND.between(0, this.sys.game.config.width);
        obj2.y = Phaser.Math.RND.between(0, this.sys.game.config.height);
        this.score.setText(`P1: ${++scoreP1}`);
    }, null, this);

    this.anims.create({
        key: 'characterIdleDown',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 0,
            end: 1,
        }),
        repeat: -1
    });

    this.anims.create({
        key: 'characterIdleRight',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 12,
            end: 13,
        }),
        repeat: -1
    });

    this.anims.create({
        key: 'characterIdleUp',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 4,
            end: 5,
        }),
        repeat: -1
    });

    this.anims.create({
        key: 'characterIdleLeft',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 8,
            end: 9,
        }),
        repeat: -1
    });

    this.anims.create({
        key: 'characterMoveLeft',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 8,
            end: 11
        }),
    });

    this.anims.create({
        key: 'characterMoveRight',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 12,
            end: 15
        }),
    });

    this.anims.create({
        key: 'characterMoveUp',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 4,
            end: 7
        }),
    });

    this.anims.create({
        key: 'characterMoveDown',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('character', {
            start: 0,
            end: 3
        }),
    });

    this.anims.create({
        key: 'mouseMove',
        frameRate: 7,
        frames: this.anims.generateFrameNumbers('mouse', {
            start: 0,
            end: 7,
        }),
        repeat: -1
    });

    this.character.play('characterIdleDown');
    for (const mouse of this.enemies.getChildren()) {
        mouse.play('mouseMove');
    }

    if (nrPlayers === 2) {
        this.scoreP2 = this.add.text(this.sys.game.config.width - 110, 0, `P2: ${scoreP2}`, {
            fontSize: '32px',
        })
        this.character2 = this.physics.add.sprite(window.innerWidth / 4, window.innerHeight / 4, 'character');
        this.character2.setScale(4);
        this.character2.width = 10;
        this.character2.height = 10;
        this.character2.play('characterIdleDown');
        this.physics.add.overlap(this.character2, this.enemies, (_, mouse) => {
            mouse.x = Phaser.Math.RND.between(0, this.sys.game.config.width);
            mouse.y = Phaser.Math.RND.between(0, this.sys.game.config.height);
            this.scoreP2.setText(`P2: ${++scoreP2}`);
        }, null, this);
    }
};

gameScene.update = function () {
    if (this.character) {
        if (this.cursors.left.isDown && this.character.x > 0) {
            this.character.x -= this.speed;
            this.character.play('characterMoveLeft');
            this.character.play('characterIdleLeft');
        } else if (this.cursors.right.isDown && this.character.x < this.sys.game.config.width) {
            this.character.x += this.speed;
            this.character.play('characterMoveRight');
            this.character.play('characterIdleRight');
        } else if (this.cursors.up.isDown && this.character.y > 0) {
            this.character.y -= this.speed;
            this.character.play('characterMoveUp');
            this.character.play('characterIdleUp');
        } else if (this.cursors.down.isDown && this.character.y < this.sys.game.config.height - 110) {
            this.character.y += this.speed;
            this.character.play('characterMoveDown');
            this.character.play('characterIdleDown');
        }
    }
    if (this.character2) {
        if (this.keyA.isDown && this.character2.x > 0) {
            this.character2.x -= this.speed;
            this.character2.play('characterMoveLeft');
            this.character2.play('characterIdleLeft');
        } else if (this.keyS.isDown && this.character2.y < this.sys.game.config.height - 110) {
            this.character2.y += this.speed;
            this.character2.play('characterMoveDown');
            this.character2.play('characterIdleDown');
        } else if (this.keyW.isDown && this.character2.y > 0) {
            this.character2.y -= this.speed;
            this.character2.play('characterMoveUp');
            this.character2.play('characterIdleUp');
        } else if (this.keyD.isDown && this.character2.x < this.sys.game.config.width) {
            this.character2.x += this.speed;
            this.character2.play('characterMoveRight');
            this.character2.play('characterIdleRight');
        }
    }
};

let config = {
    type: Phaser.AUTO,
    width: screen.width,
    height: screen.height,
    scene: gameScene,
    physics: {
        default: 'arcade',
        arcade: {
            debug: false,
        }
    }
};

// first screen configuration
let preGameScene = new Phaser.Scene('PreGame');

preGameScene.preload = function () {
    this.load.image('background', 'assets/tiles/Grass.png');
};

preGameScene.create = function () {
    let background = this.add.sprite(0, 0, 'background');
    background.setOrigin(0, 0);
    background.setScale(Math.max(window.innerWidth / background.width, window.innerHeight / background.height));

    this.welcomeText = this.add.text(window.outerWidth / 2.4, window.innerHeight / 6, 'Welcome', {
        fontSize: '64px',
        color: '#CD942A',
        fontFamily: 'Arial'
    });
    this.welcomeText.setOrigin(0, 0);

    let buttonOnePlayer = this.add.text(window.outerWidth / 6, window.innerHeight / 2, 'One player', {
        backgroundColor: 'red',
        fontSize: '32px',
        fontFamily: 'Arial'
    });
    buttonOnePlayer.setInteractive();
    buttonOnePlayer.on('pointerdown', () => {
        game.destroy(true);
        nrPlayers = 1;
        game = new Phaser.Game(config);
    })

    let buttonTwoPlayers = this.add.text(window.outerWidth / 1.5, window.innerHeight / 2, 'Two players', {
        backgroundColor: 'blue',
        fontSize: '32px',
        fontFamily: 'Arial'
    });
    buttonTwoPlayers.setInteractive();
    buttonTwoPlayers.on('pointerdown', () => {
        game.destroy(true);
        nrPlayers = 2;
        game = new Phaser.Game(config);
    })
};

let preGameConfig = {
    type: Phaser.AUTO,
    width: screen.width,
    height: screen.height,
    scene: preGameScene,
};

let game = new Phaser.Game(preGameConfig);
