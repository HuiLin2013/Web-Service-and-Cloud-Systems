require 'rest_client'
require 'colors'
require 'optparse'

options = {:notation => :rpn}

op = OptionParser.new do |opts|
  opts.banner = "Usage: ruby client.rb [options]"

  opts.on("-n", "--notation [NOTATION]", [:pn, :rpn, :normal],
          "Notation: pn, rpn, normal") do |v|
    options[:notation] = v
  end

  opts.on("--url URL", "Specify URL") do |v|
    options[:url] = v
  end

  opts.on_tail("-h", "--help", "Show this message") do
    puts opts
    exit
  end
end

op.parse!

if options[:url].nil?
  puts "Missing options: url"
  puts op
  exit
end

options[:url] += "/"

SYNTAX_ERROR = "syntax error"
DIV_BY_0 = "division by zero"

tests = [{:code => 200, :result => 2.0,
          :pn => "+&1&1", :rpn => "1&1&+", :normal => "1+1"},
         {:code => 400, :result => DIV_BY_0,
          :pn => ":&1&0", :rpn => "1&0&:", :normal => "1:0"},
         {:code => 400, :result => SYNTAX_ERROR,
          :pn => "+&1&1&1", :rpn => "1&1&1&+", :normal => "1+1(1)"},
         {:code => 400, :result => SYNTAX_ERROR,
          :pn => "+", :rpn => "+", :normal => "+"},
         {:code => 400, :result => SYNTAX_ERROR,
          :pn => "+&1&1&+&2&2", :rpn => "1&1&+&2&2&+", :normal => "(1+1)(2+2)"},
         {:code => 200, :result => 118.83,
          :pn => "+&:&+&-&32.48&44.96&*&71.55&55.86&53.83&44.81",
          :rpn => "32.48&44.96&-&71.55&55.86&*&+&53.83&:&44.81&+",
          :normal => "(((32.48-44.96)+(71.55*55.86)):53.83)+44.81"},
# +&:&+&-&32.48&44.96&*&71.55&55.86&53.83&44.81
# (+ (/ (+ (- 32.48 44.96) (* 71.55 55.86)) 53.83) 44.81)
# "+ : + - 32.48 44.96 * 71.55 55.86 53.83 44.81"
# "32.48&44.96&-&71.55&55.86&*&+&53.83&:&44.81&+"
# "32.48 44.96 - 71.55 55.86 * + 53.83 / 44.81 +"
# "(((32.48-44.96)+(71.55*55.86)):53.83)+44.81"
         {:code => 200, :result => 20.16,
          :pn => ":&+&14.54&*&95.71&:&88.71&13.85&31.13",
          :rpn => "14.54&95.71&88.71&13.85&:&*&+&31.13&:",
          :normal => "(14.54+(95.71*(88.71:13.85))):31.13"},
# :&+&14.54&*&95.71&:&88.71&13.85&31.13
# (/ (+ 14.54 (* 95.71 (/ 88.71 13.85))) 31.13)
# ": + 14.54 * 95.71 : 88.71 13.85 31.13"
# "14.54&95.71&88.71&13.85&:&*&+&31.13&:"
# "14.54 95.71 88.71 13.85 / * + 31.13 /"
# "(14.54+(95.71*(88.71:13.85))):31.13"
         {:code => 200, :result => 201.48,
          :pn => "+&-&91.63&-&:&31.19&77.69&+&45.44&54.57&10.24",
          :rpn => "91.63&31.19&77.69&:&45.44&54.57&+&-&-&10.24&+",
          :normal => "(91.63-((31.19:77.69)-(45.44+54.57)))+10.24"},
# +&-&91.63&-&:&31.19&77.69&+&45.44&54.57&10.24
# (+ (- 91.63 (- (/ 31.19 77.69) (+ 45.44 54.57))) 10.24)
# "+ - 91.63 - : 31.19 77.69 + 45.44 54.57 10.24"
# "91.63&31.19&77.69&:&45.44&54.57&+&-&-&10.24&+"
# "91.63 31.19 77.69 / 45.44 54.57 + - - 10.24 +"
# "(91.63-((31.19:77.69)-(45.44+54.57)))+10.24"
         {:code => 200, :result => -7172.59,
          :pn => "+&14.69&+&*&:&+&51.74&:&15.41&91.98&+&-&61.35&7.54&-&15.04&99.91&*&*&-&25.52&23.58&:&40.87&98.98&:&+&84.69&72.81&+&98.35&28.84&-&17.66&+&*&+&70.49&4.16&95.24&-&93.76&:&13.11&90.59",
          :rpn => "14.69&51.74&15.41&91.98&:&+&61.35&7.54&-&15.04&99.91&-&+&:&25.52&23.58&-&40.87&98.98&:&*&84.69&72.81&+&98.35&28.84&+&:&*&*&17.66&70.49&4.16&+&95.24&*&93.76&13.11&90.59&:&-&+&-&+&+",
          :normal => "14.69+((((51.74+(15.41:91.98)):((61.35-7.54)+(15.04-99.91)))*(((25.52-23.58)*(40.87:98.98))*((84.69+72.81):(98.35+28.84))))+(17.66-(((70.49+4.16)*95.24)+(93.76-(13.11:90.59)))))"},
# +&14.69&+&*&:&+&51.74&:&15.41&91.98&+&-&61.35&7.54&-&15.04&99.91&*&*&-&25.52&23.58&:&40.87&98.98&:&+&84.69&72.81&+&98.35&28.84&-&17.66&+&*&+&70.49&4.16&95.24&-&93.76&:&13.11&90.59
# (+ 14.69 (+ (* (/ (+ 51.74 (/ 15.41 91.98)) (+ (- 61.35 7.54) (- 15.04 99.91))) (* (* (- 25.52 23.58) (/ 40.87 98.98)) (/ (+ 84.69 72.81) (+ 98.35 28.84)))) (- 17.66 (+ (* (+ 70.49 4.16) 95.24) (- 93.76 (/ 13.11 90.59))))))
# "+ 14.69 + * : + 51.74 : 15.41 91.98 + - 61.35 7.54 - 15.04 99.91 * * - 25.52 23.58 : 40.87 98.98 : + 84.69 72.81 + 98.35 28.84 - 17.66 + * + 70.49 4.16 95.24 - 93.76 : 13.11 90.59"
# "14.69&51.74&15.41&91.98&:&+&61.35&7.54&-&15.04&99.91&-&+&:&25.52&23.58&-&40.87&98.98&:&*&84.69&72.81&+&98.35&28.84&+&:&*&*&17.66&70.49&4.16&+&95.24&*&93.76&13.11&90.59&:&-&+&-&+&+"
# "14.69 51.74 15.41 91.98 / + 61.35 7.54 - 15.04 99.91 - + / 25.52 23.58 - 40.87 98.98 / * 84.69 72.81 + 98.35 28.84 + / * * 17.66 70.49 4.16 + 95.24 * 93.76 13.11 90.59 / - + - + +"
# "14.69+((((51.74+(15.41:91.98)):((61.35-7.54)+(15.04-99.91)))*(((25.52-23.58)*(40.87:98.98))*((84.69+72.81):(98.35+28.84))))+(17.66-(((70.49+4.16)*95.24)+(93.76-(13.11:90.59)))))"
         {:code => 200, :result => 12365815.43,
          :pn => "*&+&+&-&:&-&36.09&26.25&36.07&46.54&+&+&58.14&*&18.56&42.02&39.02&71.4&*&-&*&34.62&*&:&17.52&72.3&51.6&59.41&36.7",
          :rpn => "36.09&26.25&-&36.07&:&46.54&-&58.14&18.56&42.02&*&+&39.02&+&+&71.4&+&34.62&17.52&72.3&:&51.6&*&*&59.41&-&36.7&*&*",
          :normal => "(((((36.09-26.25):36.07)-46.54)+((58.14+(18.56*42.02))+39.02))+71.4)*(((34.62*((17.52:72.3)*51.6))-59.41)*36.7)"},
# *&+&+&-&:&-&36.09&26.25&36.07&46.54&+&+&58.14&*&18.56&42.02&39.02&71.4&*&-&*&34.62&*&:&17.52&72.3&51.6&59.41&36.7
# (* (+ (+ (- (/ (- 36.09 26.25) 36.07) 46.54) (+ (+ 58.14 (* 18.56 42.02)) 39.02)) 71.4) (* (- (* 34.62 (* (/ 17.52 72.3) 51.6)) 59.41) 36.7))
# "* + + - : - 36.09 26.25 36.07 46.54 + + 58.14 * 18.56 42.02 39.02 71.4 * - * 34.62 * : 17.52 72.3 51.6 59.41 36.7"
# "36.09&26.25&-&36.07&:&46.54&-&58.14&18.56&42.02&*&+&39.02&+&+&71.4&+&34.62&17.52&72.3&:&51.6&*&*&59.41&-&36.7&*&*"
# "36.09 26.25 - 36.07 / 46.54 - 58.14 18.56 42.02 * + 39.02 + + 71.4 + 34.62 17.52 72.3 / 51.6 * * 59.41 - 36.7 * *"
# "(((((36.09-26.25):36.07)-46.54)+((58.14+(18.56*42.02))+39.02))+71.4)*(((34.62*((17.52:72.3)*51.6))-59.41)*36.7)"
         {:code => 200, :result => 2,
          :pn => "2",
          :rpn => "2",
          :normal => "2"}]

errors = 0
tests.each do |t|
  error = []
  begin
    r = RestClient.get "#{options[:url]}#{t[options[:notation]]}"

    if r.code != t[:code]
      error << "wrong http code (#{r.code})"
    end

    if t[:result] != (t[:result].instance_of?(String) ? r.body.downcase : r.body.to_f.round(2))
      error << "wrong result (#{r.body})"
    end
  rescue RestClient::Exception => e
    if e.http_code != t[:code]
      error << "wrong http code (#{e.http_code})"
    end

    if t[:result] != e.response.downcase
      error << "wrong error message (#{e.response})"
    end
  end

  if error.empty?
    puts "#{t[options[:notation]]} - OK!".hl(:green)
  else
    errors += 1
    puts "#{t[options[:notation]]} - #{error.join(', ')}".hl(:red)
  end
end

response = "total #{errors} errors for #{tests.length} tests, #{(100.0 * errors / tests.length).round(2)}% passed"

if errors == 0
  puts response.hl(:green)
elsif errors == tests.length
  puts response.hl(:red)
else
  puts response.hl(:yellow)
end
