import random
import json
prime_p = 177722061268479172618036265876397003509269048504268861767046046115994443156284488651055714387194800232244730303458489864174073986896097610603558449505871398422283548997781783328299833985053166443389417018452145117134522568709774729520563358860355765070837814475957567869527710749697252440829860298572991179307
prime_q = 88861030634239586309018132938198501754634524252134430883523023057997221578142244325527857193597400116122365151729244932087036993448048805301779224752935699211141774498890891664149916992526583221694708509226072558567261284354887364760281679430177882535418907237978783934763855374848626220414930149286495589653
generator = 155023513036247948265047543654868687396096131010469506673664792405197011708229660714554316186573661708325589755749538173765363486885279182722265095526282277543504738661074255038508180451933144124775391105622347889120007632958758010276343139080391061202699695556814560529729431091459632136589488173063954346793
pubkey = 93091921640159468106305784288442650651946378295897246569419220256788465679993825629909743857075454596032445144833173746431710294553277863156332557755414563641361390587128850058131535616906913180516458195808877783654160132170722826553007632858895507833301845277489859624212766615307390655119628578839365223546

y = random.randrange(prime_q)
x = (pow(generator,y,prime_p) * pubkey) % prime_p
proof = {'x' : x, 'y' : y }
proof = json.dumps(proof)
print(proof)
