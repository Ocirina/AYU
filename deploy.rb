@imports = []
@content = []
@manager = []
MAIN_CLASS = 'Manager'
PACKAGE_NAMESPACE = './src/nl/codecup/src/'

# This method handles the line given
def handle_line(file, line)
  if line.start_with? 'import'
    @imports << squish(line)
  else
    @content << clean_line(line)
  end
end

# This method cleans the line given
# - Removes whitespaces
# - Removes comments
# - Removes package namespace
# - Removes public keyword from interface or class
def clean_line(line)
  line = squish(line)
  return '' if line.start_with? 'package', '/*', '*', '*/', '//', "\n"
  line.gsub!('public', '') if line.start_with? 'public class', 'public interface', 'public abstract'
  line
end

# Hard kill all whitespaces!
# http://api.rubyonrails.org/classes/String.html#method-i-squish
def squish(str)
  str.gsub!(/\A[[:space:]]+/, '')
  str.gsub!(/[[:space:]]+\z/, '')
  str.gsub!(/[[:space:]]+/, ' ')
  str.strip
  str
end

# Loop through all files in the namespace
Dir.glob("#{PACKAGE_NAMESPACE}**/*").each do |file|
  File.open(file, 'r').each{|line| handle_line(file, line)} if File.file? file
end

# Remove all empty lines
@content.reject! { |c| c.empty? }
@manager.reject! { |c| c.empty? }

# First place all imports on top
concat  = @imports.uniq.sort.join("\n")
# Second place all content
concat << @content.join("\n")
# Make the manager the main class
concat.gsub!("class #{MAIN_CLASS}", "public class #{MAIN_CLASS}")

# Write out to a file
out_file = File.new("#{MAIN_CLASS}.java", "w")
out_file.puts concat
out_file.close
