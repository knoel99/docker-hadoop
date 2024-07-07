import os

def is_hidden(file_path):
    return os.path.basename(file_path).startswith('.')

def collect_files(root_dir, output_file):
    with open(output_file, 'w', encoding='utf-8') as outfile:
        for dirpath, dirnames, filenames in os.walk(root_dir):
            # Remove hidden directories
            dirnames[:] = [d for d in dirnames if not is_hidden(d)]
            
            for filename in filenames:
                if is_hidden(filename):
                    continue  # Skip hidden files
                
                file_path = os.path.join(dirpath, filename)
                relative_path = os.path.relpath(file_path, root_dir)
                
                outfile.write(f"File: {relative_path}\n")
                outfile.write("=" * (len(relative_path) + 6) + "\n\n")
                
                try:
                    with open(file_path, 'r', encoding='utf-8') as infile:
                        content = infile.read()
                        outfile.write(content)
                except UnicodeDecodeError:
                    outfile.write("[Binary file content not shown]\n")
                except Exception as e:
                    outfile.write(f"[Error reading file: {str(e)}]\n")
                
                outfile.write("\n\n")

# Usage
root_directory = "."  # Current directory, change this to your project root if needed
output_file = "project_files.txt"

collect_files(root_directory, output_file)
print(f"All non-hidden files have been written to {output_file}")