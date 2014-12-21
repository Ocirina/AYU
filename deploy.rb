@imports = []
@content = []
@manager = []

def handle_line(file, line)
  if line.start_with? 'import'
    @imports << squish(line)
  else
    if file.downcase.eql? './src/nl/codecup/src/manager.java'
      @manager << clean_line(line)
    else
      @content << clean_line(line)
    end
  end
end

def clean_line(line)
  line = squish(line)
  return '' if line.start_with? 'package', '/*', '*', '*/', '//', "\n"
  line.gsub!('public', '') if line.start_with? 'public class', 'public interface', 'public abstract'
  line
end

# http://api.rubyonrails.org/classes/String.html#method-i-squish
def squish(str)
  str.gsub!(/\A[[:space:]]+/, '')
  str.gsub!(/[[:space:]]+\z/, '')
  str.gsub!(/[[:space:]]+/, ' ')
  str.strip
  str
end

Dir.glob("./src/nl/codecup/src/**/*").each do |file|
  File.open(file, 'r').each{|line| handle_line(file, line)} if File.file? file
end

@content.reject! { |c| c.empty? }
@manager.reject! { |c| c.empty? }

concat  = @imports.uniq.sort.join("\n")
concat << 'public ' << @manager.join("\n")
concat << @content.join("\n")

out_file = File.new("Manager.java", "w")
out_file.puts concat
out_file.close
