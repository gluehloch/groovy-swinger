# SET release
#
# !!! Take care for the release name !!!
#
RELEASE_FLAG="groovy-swinger-0.4.0-release"

PROJECT_NAME="groovy-swinger"
BASE_URL="https://groovy-swinger.googlecode.com/svn"

echo "------------------------------------------------"
echo "Execute svn tag and branch:"
echo ""
echo "Project: $PROJECT_NAME"
echo "Version: $RELEASE_FLAG"
echo ""
echo "Tag /*and branch*/:"
echo "    $RELEASE_FLAG"
echo ""

#svn delete $BASE_URL/$PROJECT_NAME/tags/$RELEASE_FLAG -m "Error tag"
#svn delete $BASE_URL/$PROJECT_NAME/branches/$RELEASE_FLAG -m "Error branch"

svn copy $BASE_URL/trunk/$PROJECT_NAME $BASE_URL/tags/$PROJECT_NAME/$RELEASE_FLAG \
  -m "TAG: $RELEASE_FLAG"
  
#svn copy $BASE_URL/trunk/$PROJECT_NAME $BASE_URL/branches/$PROJECT_NAME/$RELEASE_FLAG \
#  -m "BRANCH: $RELEASE_FLAG"


echo "....Ready."
exit 1
