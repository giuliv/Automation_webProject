import argparse
import urllib.request
import json


# Parse the api key and product id
parser = argparse.ArgumentParser()
parser.add_argument("--apiKey", help="The Applause API Key", required=True)
parser.add_argument("--productId", help="The Product ID", required=True)
parser.add_argument("--outFile", help="The output file to write results to", required=True)
args = parser.parse_args()
apiKey = args.apiKey
productId = args.productId
outFile= args.outFile

# Get the set of versions for this product
url = 'https://api.applause.com/versions?product_id=' + productId
hdr = { 'X-Api-Key' : apiKey  }
req = urllib.request.Request(url, headers=hdr)

# Parse the response as JSON
r = urllib.request.urlopen(req).read()
versions = json.loads(r.decode('utf-8'))

# Find the latest version
latestVersion = 0
for version in versions:
    currentVersion = int(version['id'])
    releaseUrl = version['release_url']
    if currentVersion > latestVersion and not releaseUrl:
        latestVersion = currentVersion

# Print the latest version number
print("Latest version: ", latestVersion)

# Get the latest build for this version
url = 'https://api.applause.com/versions/' + str(latestVersion) + '/builds'
hdr = { 'X-Api-Key' : apiKey }
req = urllib.request.Request(url, headers=hdr)

# Parse the response as JSON
r = urllib.request.urlopen(req).read()
builds = json.loads(r.decode('utf-8'))

# Find the latest build
latestBuild = 0
latestFilename = ""
for build in builds:
    currentBuild = int(build['id'])
    filename = build['filename']
    if currentBuild > latestBuild and filename:
        latestBuild = currentBuild
        latestFilename = filename

# Print the latest build number
print("Latest build: ", latestBuild)
print("Filename: ", latestFilename)

# Write the buildId and versionId to the supplied output file
f = open(outFile, "a")
f.write("versionId=" + str(latestVersion))
f.write("\n")
f.write("buildId=" + str(latestBuild))
f.write("\n")
f.write("filename=" + latestFilename)
f.close()
