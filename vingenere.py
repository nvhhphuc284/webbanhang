from cryptography.fernet import Fernet

def vigenere(message, key):
    key_bytes = bytes(key, 'utf-8')
    key_length = len(key_bytes)
    key_index = 0
    key_offsets = [key_bytes[i % key_length] for i in range(len(message))]
    message_bytes = bytes(message, 'utf-8')
    encrypted_bytes = bytes([m + o for m, o in zip(message_bytes, key_offsets)])
    encrypted_message = encrypted_bytes.decode('utf-8')
    return encrypted_message

# Example usage
message = "Hello, World!"
key = "secret"
encrypted = vigenere(message, key)
print("Encrypted message: ", encrypted)
