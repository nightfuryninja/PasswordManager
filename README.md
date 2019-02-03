# Password Manager
### Brief Understanding of What a Password is
A password acts as a digital key that grants you access to important personal accounts. 


### What is a Password Manager?
A password manager is a useful piece of software that can help you create secure random passwords and manage the existing ones you have with ease. This helps to keep all your accounts secure but still easily accessible as Password Manager remembers the passwords instead of you.

### Why Use a Password Manager?
A password acts as a digital key that grants you access to various important personal accounts. 83% of people use the same password multiple times which causes great vulnerability with the many accounts you use. 

### What Encryption Methods Do You Use?
After the Master Password has been entered, it is stored in a mutable data type. (The mutable data type ensures that we can clean it up from memory after it has been encrypted). We immediately encrypt it with an algorithm known as PBKDF2. We have set PBKDF2 to 320,000 iterations (chosen for performance and security) with the hashing algorithm SHA-256. We have also set PBKDF2 to use a pseudorandom Salt of 64 bytes (2^64 = 18,446,744,073,709,551,616 different combinations) making it harder for the master password to be located in a Rainbow Table or Brute Forced without the Salt. The Salt does not need to be kept secret unlike the hash.

We can then use the SHA-256 hash generated by PBKDF2 as a key for AES encryption. It is important to note that AES has a block size of 16 bytes .We then create a pseudorandom Initialization Vector (IV). The IV ensures that the block of ciphertext is different each time and that way patterns cannot be identified and it is harder to decrypt. 

We are using the block cipher mode: CBC (Cipher Block Chaining) this uses the previous cipher text as an IV for the next block of data to be encrypted. This ensures that each block is different from the block before, ensuring that there are no patterns between each block, making it harder to decrypt without the key. 

### Copyright 2019 - Harry Ramsey & Fardeen Dowlut
