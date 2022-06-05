#![feature(bench_black_box)]
use openssl::sha;
use std::hint::black_box;
const HASH: [u8; 20] = [
    116, 151, 186, 240, 176, 244, 248, 161, 41, 63, 66, 221, 155, 222, 35, 87, 154, 121, 38, 34,
];

fn main() {
    let input = read_input();
    // without black_box rustc decrypts hash at compile time :(
    let decrypted = black_box(HASH)
        .iter()
        .enumerate()
        .map(|(i, val)| val ^ ((i * 173) as u8))
        .collect::<Vec<_>>();

    if input == decrypted {
        println!("Correct password!");
    } else {
        println!("Incorrect password!");
    }
}

fn read_input() -> Vec<u8> {
    let mut buffer = String::new();
    let str = match std::io::stdin().read_line(&mut buffer) {
        Ok(_) => buffer.trim().to_string(),
        Err(_) => panic!("Failed to read line"),
    };

    let mut hasher = sha::Sha1::new();
    hasher.update(str.as_ref());
    hasher.finish().to_vec()
}
